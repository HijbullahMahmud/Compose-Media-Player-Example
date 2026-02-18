package com.tenfingers.composevideoexample

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.analytics.AnalyticsListener
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.toRepeatMode
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerExample( ) {
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {

        Box(modifier = Modifier.fillMaxSize()) {
            val context = LocalContext.current

            var repeatMode by remember { mutableStateOf(RepeatMode.NONE) }

            VideoPlayer(
                mediaItems = listOf(
                    VideoPlayerMediaItem.NetworkMediaItem(
                        url = "https://ndtv24x7elemarchana.akamaized.net/hls/live/2003678/ndtv24x7/ndtv24x7master.m3u8",
                        mediaMetadata = MediaMetadata.Builder()
                            .setTitle("NDTV")
                            .setSubtitle("This is subtitle")
                            .build(),
                        mimeType = MimeTypes.APPLICATION_M3U8,
                        drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                            .setLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
                            .build(),
                    )
                ),
                handleLifecycle = true,
                autoPlay = true,
                usePlayerController = true,
                enablePip = true,
                handleAudioFocus = true,
                controllerConfig = VideoPlayerControllerConfig(
                    showSpeedAndPitchOverlay = true,
                    showSubtitleButton = false,
                    showCurrentTimeAndTotalTime = true,
                    showBufferingProgress = true,
                    showForwardIncrementButton = true,
                    showBackwardIncrementButton = true,
                    showBackTrackButton = true,
                    showNextTrackButton = true,
                    showRepeatModeButton = true,
                    controllerShowTimeMilliSeconds = 5_000,
                    controllerAutoShow = true,
                    showFullScreenButton = true
                ),
                volume = 0.0f,  // volume 0.0f to 1.0f
                repeatMode = RepeatMode.ALL,       // or RepeatMode.ALL, RepeatMode.ONE
                onCurrentTimeChanged = { // long type, current player time (millisec)
                    Log.e("CurrentTime", it.toString())
                },
                playerInstance = { // ExoPlayer instance (Experimental)
                    addAnalyticsListener(
                        object : AnalyticsListener {
                            @SuppressLint("UnsafeOptInUsageError")
                            override fun onRepeatModeChanged(
                                eventTime: AnalyticsListener.EventTime,
                                rMode: Int
                            ) {
                                repeatMode = rMode.toRepeatMode()
                                Toast.makeText(context, "repeatMode: $repeatMode", Toast.LENGTH_SHORT).show()
                            }

                            @SuppressLint("UnsafeOptInUsageError")
                            override fun onPlayWhenReadyChanged(
                                eventTime: AnalyticsListener.EventTime,
                                playWhenReady: Boolean,
                                reason: Int
                            ) {
                                Toast.makeText(context, "isPlaying = $playWhenReady", Toast.LENGTH_SHORT).show()
                            }

                            @SuppressLint("UnsafeOptInUsageError")
                            override fun onVolumeChanged(
                                eventTime: AnalyticsListener.EventTime,
                                volume: Float
                            ) {
                                Toast.makeText(context, "Player volume changed: $volume", Toast.LENGTH_SHORT).show()
                            }

                        }
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
            )
        }
    }
}