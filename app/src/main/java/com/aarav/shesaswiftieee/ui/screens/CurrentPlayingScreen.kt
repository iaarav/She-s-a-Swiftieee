package com.aarav.shesaswiftieee.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.aarav.shesaswiftieee.R
import com.aarav.shesaswiftieee.data.SWIFT

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentPlayingScreen(
    navController: NavController,
    currentPlayingSong: SWIFT,
    progress: Float,
    onProgress: (Float) -> Unit,
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Scaffold(topBar = {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Row {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous Screen",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                    Spacer(modifier = Modifier.width(25.dp))
                    Text(text = "Current Playing Song")
                }
            }
        }) {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    AsyncImage(
                        model = currentPlayingSong.imageURL,
                        contentDescription = "${currentPlayingSong.title} image"
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "${currentPlayingSong.title}")
                    Text(text = "${currentPlayingSong.singer}")
                    Spacer(modifier = Modifier.height(30.dp))
                    Slider(
                        value = progress, onValueChange = { onProgress(it) }, valueRange = 0f..100f
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.Center) {

                        PlayerIconItem(icon = R.drawable.playpreviousbutton) { onPrevious() }
                        PlayerIconItem(
                            icon = if (isAudioPlaying) R.drawable.pause
                            else R.drawable.play
                        ) { onStart() }
                        PlayerIconItem(icon = R.drawable.playnextbutton) { onNext() }

                    }
                }
            }
        }
    }
}
