package com.example.skills.view.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skills.R
import com.example.skills.viewmodel.VideoState
import com.example.skills.viewmodel.VideoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoControls(
    viewModel: VideoViewModel,
    state: VideoState,
    fullscreen: Boolean,
    onFullscreenClick: () -> Unit,
    onTouch: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSpeedMenu by remember { mutableStateOf(false) }

    var dragging by remember { mutableStateOf(false) }
    var dragPosition by remember { mutableStateOf(0L) }

    val shownPosition = if (dragging) dragPosition else state.position

    var progress = 0f
    if (state.duration > 0) {
        progress = shownPosition.toFloat() / state.duration
    }
    if (progress > 1f) {
        progress = 1f
    }

    val mainColor = MaterialTheme.colorScheme.primary
    val textColor = LocalContentColor.current
    val lineColor = textColor.copy(alpha = 0.3f)

    Column(modifier = modifier.fillMaxWidth()) {

        Slider(
            value = shownPosition.toFloat(),
            valueRange = 0f..maxOf(state.duration, 1L).toFloat(),
            onValueChange = {
                onTouch()
                dragging = true
                dragPosition = it.toLong()
            },
            onValueChangeFinished = {
                if (dragging) {
                    viewModel.seekTo(dragPosition)
                }
                dragging = false
            },
            thumb = {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(mainColor, CircleShape)
                )
            },
            track = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(lineColor, CircleShape)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(4.dp)
                            .background(mainColor, CircleShape)
                    )
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatTime(shownPosition) + " / " + formatTime(state.duration),
                style = MaterialTheme.typography.labelLarge
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    TextButton(
                        onClick = {
                            onTouch()
                            showSpeedMenu = true
                        }
                    ) {
                        Text(text = "${state.speed}x", color = textColor)
                    }
                    DropdownMenu(
                        expanded = showSpeedMenu,
                        onDismissRequest = { showSpeedMenu = false }
                    ) {
                        for (speed in listOf(0.5f, 1f, 1.5f, 2f)) {
                            DropdownMenuItem(
                                text = { Text("${speed}x") },
                                onClick = {
                                    onTouch()
                                    viewModel.changeSpeed(speed)
                                    showSpeedMenu = false
                                }
                            )
                        }
                    }
                }

                ControlIcon(
                    icon = if (state.loop) Icons.Filled.RepeatOne else Icons.Filled.Repeat,
                    text = stringResource(R.string.video_loop),
                    onClick = {
                        onTouch()
                        viewModel.loopOnOff()
                    }
                )

                ControlIcon(
                    icon = if (state.muted) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp,
                    text = stringResource(R.string.video_sound),
                    onClick = {
                        onTouch()
                        viewModel.muteOrUnmute()
                    }
                )

                ControlIcon(
                    icon = if (fullscreen) Icons.Filled.FullscreenExit else Icons.Filled.Fullscreen,
                    text = stringResource(R.string.video_fullscreen),
                    onClick = {
                        onTouch()
                        onFullscreenClick()
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ControlIcon(
                icon = Icons.Filled.Replay10,
                text = stringResource(R.string.video_rewind),
                size = 32,
                onClick = {
                    onTouch()
                    viewModel.jump(-10)
                }
            )

            Spacer(modifier = Modifier.width(24.dp))

            FilledIconButton(
                onClick = {
                    onTouch()
                    viewModel.playOrPause()
                },
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = if (state.playing) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = stringResource(
                        if (state.playing) R.string.video_pause else R.string.video_play
                    ),
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            ControlIcon(
                icon = Icons.Filled.Forward10,
                text = stringResource(R.string.video_forward),
                size = 32,
                onClick = {
                    onTouch()
                    viewModel.jump(10)
                }
            )
        }
    }
}

@Composable
fun ControlIcon(
    icon: ImageVector,
    text: String,
    size: Int = 24,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(size.dp)
        )
    }
}

fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}