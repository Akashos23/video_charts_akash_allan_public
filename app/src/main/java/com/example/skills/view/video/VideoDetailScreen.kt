package com.example.skills.view.video

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.skills.R
import com.example.skills.viewmodel.ListingsViewModel
import com.example.skills.viewmodel.VideoViewModel

@Composable
fun VideoDetailScreen(
    navController: NavController,
    id: Int,
    listingsViewModel: ListingsViewModel = viewModel(),
    videoViewModel: VideoViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val view = LocalView.current

    val productName = listingsViewModel.getProductName(id)
    val state by videoViewModel.state.collectAsStateWithLifecycle()

    var fullscreen by rememberSaveable { mutableStateOf(false) }
    var showPublishDialog by rememberSaveable { mutableStateOf(false) }
    var cameraVideo by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(id) {
        videoViewModel.open(id)
    }

    LaunchedEffect(fullscreen) {
        setFullscreen(activity, fullscreen)
    }

    BackHandler(enabled = fullscreen) {
        fullscreen = false
    }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        if (activity?.isChangingConfigurations != true) {
            videoViewModel.pause()
        }
    }

    DisposableEffect(state.playing) {
        view.keepScreenOn = state.playing
        onDispose { view.keepScreenOn = false }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        // TODO 6: the user chose a video in the gallery
        // If uri is not null, publish the video with videoViewModel.publish(...)
        // publish needs a String, use uri.toString()

    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CaptureVideo()
    ) { saved ->
        // BONUS D: if the video was saved, publish cameraVideo
        // else delete the empty video with context.contentResolver.delete(Uri.parse(cameraVideo), null, null)
    }

    fun recordWithCamera() {
        val info = ContentValues()
        info.put(MediaStore.Video.Media.DISPLAY_NAME, "video_" + System.currentTimeMillis() + ".mp4")
        info.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        val uri = context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, info)

        if (uri != null) {
            cameraVideo = uri.toString()
            try {
                cameraLauncher.launch(uri)
            } catch (e: ActivityNotFoundException) {
                videoViewModel.showError(context.getString(R.string.video_no_camera))
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = if (fullscreen) Color.Black else MaterialTheme.colorScheme.background
    ) {
        if (fullscreen) {
            FullscreenVideo(
                viewModel = videoViewModel,
                state = state,
                onExitClick = { fullscreen = false }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.video_back)
                        )
                    }
                    Text(
                        text = stringResource(R.string.video_page_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    VideoFrame(
                        viewModel = videoViewModel,
                        state = state,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    if (state.video != null) {
                        VideoControls(
                            viewModel = videoViewModel,
                            state = state,
                            fullscreen = false,
                            onFullscreenClick = { fullscreen = true },
                            onTouch = { }
                        )
                    }

                    Text(
                        text = productName,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Button(
                        onClick = { showPublishDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.FileUpload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.video_publish))
                    }

                    if (state.video != null) {
                        OutlinedButton(
                            onClick = { videoViewModel.delete() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.video_delete))
                        }
                    }
                }
            }
        }
    }

    if (showPublishDialog) {
        PublishDialog(
            demoLink = videoViewModel.demoLink,
            onCancel = { showPublishDialog = false },
            onGalleryClick = {
                showPublishDialog = false
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                )
            },
            onCameraClick = {
                showPublishDialog = false
                recordWithCamera()
            },
            onLinkClick = { link ->
                showPublishDialog = false
                videoViewModel.publish(link)
            }
        )
    }
}

@Composable
fun PublishDialog(
    demoLink: String,
    onCancel: () -> Unit,
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onLinkClick: (String) -> Unit
) {
    var link by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(stringResource(R.string.video_publish)) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledTonalButton(
                    onClick = onGalleryClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.VideoLibrary, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.video_from_gallery))
                }

                if (Build.VERSION.SDK_INT >= 29) {
                    FilledTonalButton(
                        onClick = onCameraClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Videocam, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.video_from_camera))
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                OutlinedTextField(
                    value = link,
                    onValueChange = { link = it },
                    label = { Text(stringResource(R.string.video_link_label)) },
                    leadingIcon = { Icon(Icons.Filled.Link, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                TextButton(onClick = { link = demoLink }) {
                    Text(stringResource(R.string.video_use_demo_link))
                }
                Button(
                    onClick = { onLinkClick(link.trim()) },
                    enabled = link.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.video_publish_link))
                }
            }
        },
        confirmButton = { },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(R.string.video_cancel))
            }
        }
    )
}


fun setFullscreen(activity: Activity?, on: Boolean) {
    if (activity == null) return
    val bars = WindowCompat.getInsetsController(activity.window, activity.window.decorView)

    // BONUS E: if on is true, force the landscape orientation and hide the system bars
    // if on is false, put the orientation back to normal and show the bars
    // use activity.requestedOrientation, ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE,
    // ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED, bars.hide(...), bars.show(...)
}