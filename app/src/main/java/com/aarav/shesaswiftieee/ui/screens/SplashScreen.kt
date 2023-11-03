package com.aarav.shesaswiftieee.ui.screens

import android.window.SplashScreen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.aarav.shesaswiftieee.navigation.AppScreens
import com.aarav.shesaswiftieee.ui.ViewModel.SongViewModel

@Composable
fun SplashScreen(navController: NavController, songViewModel: SongViewModel) {
    if (songViewModel.songData.value.loading == true) {
        CircularProgressIndicator()
    } else {
        navController.navigate(AppScreens.HomeScreen.name)
    }
}