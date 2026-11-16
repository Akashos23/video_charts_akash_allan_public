package com.example.skills.view.video

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_TEXTURE_VIEW
import com.example.skills.R
import com.example.skills.viewmodel.VideoState
import com.example.skills.viewmodel.VideoViewModel
import kotlinx.coroutines.delay

@Composable
fun VideoFrame(
    viewModel: VideoViewModel,
    state: VideoState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (state.video != null) {
            VideoSurface(viewModel.player, state.ratio)
        }

        val error = state.error
        if (error != null) {
            ErrorMessage(error, onRetry = { viewModel.retry() })
        } else if (state.video == null) {
            Text(text = stringResource(R.string.video_empty), color = Color.White)
        } else if (state.loading) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}


@Composable
fun FullscreenVideo(
    viewModel: VideoViewModel,
    state: VideoState,
    onExitClick: () -> Unit
) {
    var showControls by remember { mutableStateOf(true) }
    var touches by remember { mutableStateOf(0) }

    LaunchedEffect(showControls, state.playing, touches) {
        // BONUS F: if the controls are shown and the video is playing,
        // wait 5 seconds with delay(...), then hide the controls
    }

    LaunchedEffect(state.playing) {
        if (!state.playing) {
            showControls = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { showControls = !showControls })
            }
    ) {
        VideoFrame(
            viewModel = viewModel,
            state = state,
            modifier = Modifier.fillMaxSize()
        )

        if (showControls && state.video != null) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.6f),
                contentColor = Color.White
            ) {
                VideoControls(
                    viewModel = viewModel,
                    state = state,
                    fullscreen = true,
                    onFullscreenClick = onExitClick,
                    onTouch = { touches++ },
                    modifier = Modifier
                        .safeDrawingPadding()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoSurface(player: Player, ratio: Float) {
    // TODO 3: show the video with PlayerSurface
    // (the player, Modifier.aspectRatio(ratio), SURFACE_TYPE_TEXTURE_VIEW)
}

@Composable
fun ErrorMessage(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = stringResource(R.string.video_error), color = Color.White)
        Text(text = error, color = Color.LightGray)
        Button(onClick = onRetry) {
            Icon(Icons.Filled.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.video_retry))
        }
    }
}