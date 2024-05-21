@file:OptIn(ExperimentalMaterial3Api::class)

package com.aarav.shesaswiftieee.ui.screens.songDetailScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.ui.screens.homeScreen.components.Constants.MainColour
import com.aarav.shesaswiftieee.ui.screens.songDetailScreen.components.MusicItem
import com.aarav.shesaswiftieee.ui.screens.songDetailScreen.components.MusicMiniPlayerCard
import com.aarav.shesaswiftieee.ui.viewModel.shared.MusicPlaybackUIState
import com.aarav.shesaswiftieee.ui.viewModel.song.HomeEvent
import com.aarav.shesaswiftieee.ui.viewModel.song.HomeUiState
import com.aarav.shesaswiftieee.ui.viewModel.song.SongViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongDetailScreen(
    albumName: String?,
    navController: NavController,
    songVM: SongViewModel,
    onEvent: (HomeEvent) -> Unit,
    homeUiState: HomeUiState,
    musicPlaybackUiState: MusicPlaybackUIState,
    onNavigateToMusicPlayer: () -> Unit
) {
    val data = songVM.searchAlbum(albumName!!)
    val snackbarHostState = remember { SnackbarHostState() }

    if (data.isNotEmpty()) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(modifier = Modifier, colors = TopAppBarDefaults.topAppBarColors(
                    Color(MainColour)
                ), title = {
                    Row (horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                        ){
                        IconButton(
                            modifier = Modifier.size(24.dp),
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "Minimize music player"
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                            Text(text = albumName)
                    }
                })
            }
        ) { innerpadding ->
            with(homeUiState) {
                when {
                    loading == true -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    loading == false && errorMessage == null -> {
                        if (musics != null) {
                            Box(modifier = Modifier.padding(top = 10.dp)) {
                                LazyColumn(
                                    modifier = Modifier.padding(innerpadding),
                                    contentPadding = PaddingValues(bottom = 80.dp)
                                ) {
                                    items(data) {
                                        MusicItem(
                                            music = it,
                                            onClick = {
                                                songVM.addMediaItemsByAlbum(it.album!!)
                                                onEvent(HomeEvent.OnMusicSelected(it))
                                                onEvent(HomeEvent.PlayMusic)
                                            }
                                        )
                                    }
                                }

                                with(musicPlaybackUiState) {
                                    if (playerState != PlayerState.STOPPED) {
                                        MusicMiniPlayerCard(
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .align(Alignment.BottomCenter),
                                            music = currentMusic,
                                            playerState = playerState,
                                            onResumeClicked = { onEvent(HomeEvent.ResumeMusic) },
                                            onPauseClicked = { onEvent(HomeEvent.PauseMusic) },
                                            onClick = { onNavigateToMusicPlayer() }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

