package com.aarav.shesaswiftieee.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.aarav.shesaswiftieee.ui.screens.songDetailScreen.components.MusicItem
import com.aarav.shesaswiftieee.ui.screens.songDetailScreen.components.MusicMiniPlayerCard
import com.aarav.shesaswiftieee.ui.viewModel.shared.MusicPlaybackUIState
import com.aarav.shesaswiftieee.ui.viewModel.song.HomeEvent
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
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    if (!songData.data.isNullOrEmpty()) {
        val sortedSongData = getSortedAlbum(songData.data!!).entries.toList()

        Column {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 20.dp, bottomStart = 20.dp
                        )
                    ),
                color = Color(MainColour)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "She's a Swiftieee")

                    SearchBar(query = text,
                        onQueryChange = { text = it
                                        },
                        onSearch = { active = false },
                        active = active,
                        onActiveChange = { active = it },
                        placeholder = { Text(text = "Search") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Search Icon"
                            )
                        },
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (text.isNotEmpty()) {
                                        text = ""
                                    } else {
                                        active = false
                                    }
                                },
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Close Icon"
                            )
                        }) {
                        val searchedSongs = songViewModel.searchSongs(text)

                        LazyColumn(
                            modifier = Modifier.padding(),
                            contentPadding = PaddingValues(bottom = 80.dp)
                        ) {
                            items(searchedSongs) {
                                MusicItem(music = it, onClick = {
                                    songViewModel.addMediaItemsByAlbum(it.album!!)
                                    onEvent(HomeEvent.OnMusicSelected(it))
                                    onEvent(HomeEvent.PlayMusic)
                                })
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
            Box {
                Surface(modifier = Modifier.padding(top = 20.dp)) {
                    Column {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2), modifier = Modifier.padding()
                        ) {
                            items(sortedSongData) { (album, songs) ->
                                AlbumView(album = album.toString(), songs) {
//                                    songViewModel.addMediaItemsByAlbum(album!!)
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
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    strokeWidth = 5.dp, // Adjust thickness as needed
                )
            }
        }
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
                    contentScale = ContentScale.FillBounds
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

