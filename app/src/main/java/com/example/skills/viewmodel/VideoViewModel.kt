package com.example.skills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.exoplayer.ExoPlayer
import com.example.skills.model.Repositories.repository.VideoCatalog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class VideoState(
    val video: String? = null,
    val playing: Boolean = false,
    val loading: Boolean = false,
    val muted: Boolean = false,
    val loop: Boolean = false,
    val speed: Float = 1f,
    val position: Long = 0,
    val duration: Long = 0,
    val ratio: Float = 16f / 9f,
    val error: String? = null
)

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    val demoLink = VideoCatalog.DEMO_LINK

    val player = ExoPlayer.Builder(application).build()

    private val _state = MutableStateFlow(VideoState())
    val state: StateFlow<VideoState> = _state

    private var productId = -1

    private val listener = object : Player.Listener {

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _state.value = _state.value.copy(playing = isPlaying)
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            val loading = playbackState == Player.STATE_BUFFERING
            _state.value = _state.value.copy(loading = loading)
        }

        override fun onVideoSizeChanged(videoSize: VideoSize) {
            if (videoSize.width > 0 && videoSize.height > 0) {
                val ratio = videoSize.width.toFloat() / videoSize.height
                _state.value = _state.value.copy(ratio = ratio)
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            _state.value = _state.value.copy(error = error.errorCodeName, loading = false)
        }
    }

    init {
        player.addListener(listener)

        viewModelScope.launch {
            while (true) {
                _state.value = _state.value.copy(
                    position = player.currentPosition,
                    duration = maxOf(player.duration, 0L)
                )
                delay(500)
            }
        }
    }

    fun open(id: Int) {
        if (id == productId) return
        productId = id
        val link = VideoCatalog.getVideo(id)
        if (link != null) {
            load(link, false)
        }
    }

    fun publish(link: String) {
        VideoCatalog.saveVideo(productId, link)
        load(link, true)
    }

    fun delete() {
        VideoCatalog.deleteVideo(productId)
        player.stop()
        player.clearMediaItems()
        _state.value = _state.value.copy(video = null, error = null, position = 0, duration = 0)
    }

    private fun load(link: String, autoPlay: Boolean) {
        _state.value = _state.value.copy(video = link, error = null)

        // TODO 2: give the video to the player (MediaItem.fromUri, setMediaItem, prepare)
        // then set playWhenReady to autoPlay
    }

    fun playOrPause() {
        // TODO 4: pause the video if it is playing, else play it
        // if the video is finished, go back to 0 first
    }

    fun pause() {
        player.pause()
    }

    fun seekTo(position: Long) {
        // TODO 5: move the player to this position and update the state
    }

    fun jump(seconds: Int) {
        var newPosition = player.currentPosition + seconds * 1000
        if (newPosition < 0) {
            newPosition = 0
        }
        if (player.duration > 0 && newPosition > player.duration) {
            newPosition = player.duration
        }
        seekTo(newPosition)
    }

    fun changeSpeed(speed: Float) {
        // BONUS A: change the speed with setPlaybackSpeed and update the state
    }

    fun muteOrUnmute() {
        // BONUS B: mute or unmute with player.volume (0f or 1f) and update the state
    }

    fun loopOnOff() {
        // BONUS C: loop on or off with player.repeatMode and update the state
    }

    fun retry() {
        _state.value = _state.value.copy(error = null)
        player.prepare()
    }

    fun showError(text: String) {
        _state.value = _state.value.copy(error = text)
    }

    override fun onCleared() {
        // TODO 7: remove the listener and release the player
    }
}