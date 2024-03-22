package com.aarav.shesaswiftieee.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.ui.screens.homeScreen.components.Constants.MainColour
import com.aarav.shesaswiftieee.ui.screens.homeScreen.components.getSortedAlbum
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.ui.navigation.AppScreens
import com.aarav.shesaswiftieee.ui.screens.songDetailScreen.components.MusicMiniPlayerCard
import com.aarav.shesaswiftieee.ui.viewModel.shared.MusicPlaybackUIState
import com.aarav.shesaswiftieee.ui.viewModel.song.HomeEvent
import com.aarav.shesaswiftieee.ui.viewModel.song.HomeUiState
import com.aarav.shesaswiftieee.ui.viewModel.song.SongViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    songViewModel: SongViewModel,
    navController: NavController,
    onEvent: (HomeEvent) -> Unit,
    musicPlaybackUiState: MusicPlaybackUIState,
    onNavigateToMusicPlayer: () -> Unit
) {
    val songData = songViewModel.songData.value

    if (!songData.data.isNullOrEmpty()) {
        val sortedSongData = getSortedAlbum(songData.data!!).entries.toList()

        Scaffold(modifier = Modifier.fillMaxWidth(), topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(Color.Blue)
                    .height(40.dp)
                    .fillMaxWidth(), title = {
                    Text("She's a Swiftieee")
                }, colors = topAppBarColors(
                    Color(MainColour)
                )
            )
        }) { innerPadding ->
            Box {
                Surface(modifier = Modifier.padding(top = 40.dp)) {
                    Column {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(sortedSongData.size),
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            items(sortedSongData) { (album, songs) ->
                                AlbumView(album = album.toString(), songs) {
                                    navController.navigate(AppScreens.SongDetailScreen.name + "/$album")
                                }
                            }
                        }
                    }

                    with(musicPlaybackUiState) {
                        if (playerState != PlayerState.STOPPED) {
                            MusicMiniPlayerCard(modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.BottomCenter),
                                music = currentMusic,
                                playerState = playerState,
                                onResumeClicked = { onEvent(HomeEvent.ResumeMusic) },
                                onPauseClicked = { onEvent(HomeEvent.PauseMusic) },
                                onClick = { onNavigateToMusicPlayer() })
                        }
                    }

                }
            }
        }
    } else if (songData.loading == true) {
        CircularProgressIndicator()
    }
}


@Composable
fun AlbumView(
    album: String, songs: List<SWIFT>, onItemCLick: (String) -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth(0.5f)
        .padding(12.dp)
        .clickable {
            onItemCLick(album)
        }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(5.dp))
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                AsyncImage(
                    model = songs[0].imageURL,
                    contentDescription = "$album image poster",
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, Color.Black
                                ), startY = 300f
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(album, style = TextStyle(Color.White, fontSize = 15.sp))
                }
            }
        }
    }
}

