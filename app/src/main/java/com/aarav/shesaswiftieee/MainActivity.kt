package com.aarav.shesaswiftieee

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aarav.shesaswiftieee.ui.ViewModel.SongViewModel
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.navigation.AppNavigation
import com.aarav.shesaswiftieee.player.service.PlaybackService
import com.aarav.shesaswiftieee.ui.ViewModel.AudioViewModel
import com.aarav.shesaswiftieee.ui.ViewModel.UIEvents
import com.aarav.shesaswiftieee.ui.screens.HomeScreen
import com.aarav.shesaswiftieee.ui.theme.ShesASwiftieeeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val songViewModel: SongViewModel by viewModels()
    private val audioViewModel: AudioViewModel by viewModels()
    private var isServiceRunning = false

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShesASwiftieeeTheme {
                AppNavigation(
                    songViewModel = songViewModel,
                    currentPlayingAudio = audioViewModel.currentSelectedAudio,
                    isAudioPlaying = audioViewModel.isPlaying,
                    onStart = {
                        audioViewModel.onUiEvents(UIEvents.PlayPause)
                    },
                    onItemClick = {
                        audioViewModel.onUiEvents(UIEvents.SelectedAudioChange(it))
                        startService()
                    },
                    onNext = {
                        audioViewModel.onUiEvents(UIEvents.SeekToNext)
                    },
                    onPrevious = {
                        audioViewModel.onUiEvents(UIEvents.SeekToPrevious)
                    },
                    progress = audioViewModel.progress,
                    onProgress = {
                        audioViewModel.onUiEvents(UIEvents.SeekTo(it))
                    }
                )
            }
        }
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, PlaybackService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }
}