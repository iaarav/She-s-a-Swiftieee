package com.aarav.shesaswiftieee

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.aarav.shesaswiftieee.player.service.MusicPlaybackService
import com.aarav.shesaswiftieee.ui.navigation.AppNavigation
import com.aarav.shesaswiftieee.ui.theme.ShesASwiftieeeTheme
import com.aarav.shesaswiftieee.ui.viewModel.shared.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {


    private val sharedViewModel: SharedViewModel by viewModel()
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShesASwiftieeeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        sharedViewModel = sharedViewModel
                    )
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()

        sharedViewModel.destroyMediaController()
        stopService(Intent(this, MusicPlaybackService::class.java))
    }
}

