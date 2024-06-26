package com.aarav.shesaswiftieee.ui.screens.musicPlayerScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.RepeatOne
import androidx.compose.material.icons.rounded.RepeatOneOn
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.ShuffleOn
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.ui.screens.musicPlayerScreen.components.toTime
import com.aarav.shesaswiftieee.ui.viewModel.musicPlayer.MusicPlayerEvent
import com.aarav.shesaswiftieee.ui.viewModel.shared.MusicPlaybackUIState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentPlayingScreen(
    onNavigateUp: () -> Unit,
    onEvent: (MusicPlayerEvent) -> Unit,
    musicPlaybackUIState: MusicPlaybackUIState
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { onNavigateUp() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Minimize music player"
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                with(musicPlaybackUIState) {
                    currentMusic?.run {
                        AsyncImage(
                            modifier = Modifier
                                .size(280.dp)
                                .clip(MaterialTheme.shapes.large),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageURL)
                                .build(),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "Music cover"
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = title!!,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = singer!!,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // Music Playback
                    Spacer(modifier = Modifier.height(30.dp))
                    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                        Slider(
                            value = currentPosition.toFloat(),
                            valueRange = 0f..totalDuration.toFloat(),
                            onValueChange = {
                                onEvent(MusicPlayerEvent.SeekMusicPosition(it.toLong()))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = currentPosition.toTime(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = totalDuration.toTime(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    if (isShuffleEnabled) {
                                        onEvent(MusicPlayerEvent.SetMusicShuffleEnabled(false))
                                    } else {
                                        onEvent(MusicPlayerEvent.SetMusicShuffleEnabled(true))
                                    }
                                },
                            imageVector = if (isShuffleEnabled) {
                                Icons.Rounded.ShuffleOn
                            } else {
                                Icons.Rounded.Shuffle
                            },
                            contentDescription = "Shuffle button"
                        )
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { onEvent(MusicPlayerEvent.SkipPreviousMusic) },
                            imageVector = Icons.Rounded.SkipPrevious,
                            contentDescription = "Skip previous button"
                        )
                        Icon(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .clickable {
                                    when (playerState) {
                                        PlayerState.PLAYING -> onEvent(MusicPlayerEvent.PauseMusic)
                                        PlayerState.PAUSED -> onEvent(MusicPlayerEvent.ResumeMusic)
                                        else -> {}
                                    }
                                },
                            imageVector = if (playerState == PlayerState.PLAYING) {
                                Icons.Rounded.PauseCircle
                            } else {
                                Icons.Rounded.PlayCircle
                            },
                            contentDescription = "Play or pause button"
                        )
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { onEvent(MusicPlayerEvent.SkipNextMusic) },
                            imageVector = Icons.Rounded.SkipNext,
                            contentDescription = "Skip next button"
                        )
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    if (isRepeatOneEnabled) {
                                        onEvent(MusicPlayerEvent.SetPlayerRepeatOneEnabled(false))
                                    } else {
                                        onEvent(MusicPlayerEvent.SetPlayerRepeatOneEnabled(true))
                                    }
                                },
                            imageVector = if (isRepeatOneEnabled) {
                                Icons.Rounded.RepeatOneOn
                            } else {
                                Icons.Rounded.RepeatOne
                            },
                            contentDescription = "Repeat button"
                        )
                    }
                }
            }
        }
    }
}