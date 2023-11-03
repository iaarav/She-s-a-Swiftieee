@file:OptIn(ExperimentalMaterial3Api::class)

package com.aarav.shesaswiftieee.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.aarav.shesaswiftieee.R
import com.aarav.shesaswiftieee.Repository.FireRepository
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.navigation.AppScreens
import com.aarav.shesaswiftieee.ui.ViewModel.SongViewModel
import com.aarav.shesaswiftieee.ui.theme.ShesASwiftieeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SongDetailScreen(
    navController: NavController,
    albumName: String?,
    songViewModel: SongViewModel,
    currentPlayingAudio: SWIFT,
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onItemClick: (Int) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    val selectedAlbumData = songViewModel.songData.value.data?.filter { song ->
        song.album == albumName
    }?.sortedBy { it.mediaID }?.toList()


    Scaffold(bottomBar = {
        BottomBarPlayer(
            audio = currentPlayingAudio,
            isAudioPlaying = isAudioPlaying,
            onNext = onNext,
            onStart = onStart,
            onPrevious = onPrevious
        ) {
            navController.navigate(AppScreens.CurrentPlayingScreen.name)
        }
    }) {
        LazyColumn {
            itemsIndexed(selectedAlbumData!!) { index, item ->
                SongRow(song = item) {
                    onItemClick(index)
                }
            }
        }
    }
}

@Composable
fun SongRow(song: SWIFT, onItemClick: () -> Unit = {}) {
    Surface(modifier = Modifier
        .fillMaxWidth(0.98f)
        .padding(8.dp)
        .clip(RoundedCornerShape(12.dp))
        .clickable {
            onItemClick()
        }) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = song.mediaID + ".", maxLines = 1
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = song.title.toString(),
                    maxLines = 1,
                    style = MaterialTheme.typography.displayMedium
                )
            }
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = song.singer.toString(),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.DarkGray
                )
            }

            Divider()
        }
    }
}

@Composable
fun BottomBarPlayer(
    audio: SWIFT,
    isAudioPlaying: Boolean,
    onNext: () -> Unit,
    onStart: () -> Unit,
    onPrevious: () -> Unit,
    onBottomBarClicked: () -> Unit
) {
    BottomAppBar(content = {
        Surface(modifier = Modifier
            .clickable {
                onBottomBarClicked()
            }
            .fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                Row {
                    AsyncImage(
                        model = audio.imageURL,
                        contentDescription = "Current Song Picture",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = audio.title.toString(),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = audio.singer.toString(),
                            maxLines = 1,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Row {

                        PlayerIconItem(icon = R.drawable.playpreviousbutton) { onPrevious() }
                        PlayerIconItem(
                            icon = if (isAudioPlaying) R.drawable.pause
                            else R.drawable.play
                        ) { onStart() }
                        PlayerIconItem(icon = R.drawable.playnextbutton) {
                            onNext()
                        }
                    }
                }
            }
        }
    })
}


@Composable
fun PlayerIconItem(
    icon: Int,
    onClick: () -> Unit,
) {
    Surface(shape = CircleShape, modifier = Modifier
        .clip(CircleShape)
        .clickable {
            onClick()
        }) {
        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(painter = painterResource(id = icon), contentDescription = null)
        }
    }
}
