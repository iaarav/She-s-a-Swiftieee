package com.aarav.shesaswiftieee.navigation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.ui.ViewModel.AudioViewModel
import com.aarav.shesaswiftieee.ui.ViewModel.SongViewModel
import com.aarav.shesaswiftieee.ui.screens.CurrentPlayingScreen
import com.aarav.shesaswiftieee.ui.screens.HomeScreen
import com.aarav.shesaswiftieee.ui.screens.SongDetailScreen
import com.aarav.shesaswiftieee.ui.screens.SplashScreen

@Composable
fun AppNavigation(
    songViewModel: SongViewModel,
    currentPlayingAudio: SWIFT,
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onItemClick: (Int) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    progress: Float,
    onProgress: (Float) -> Unit

) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = AppScreens.SplashScreen.name
    ) {
        composable(AppScreens.SplashScreen.name) {
            SplashScreen(songViewModel = songViewModel, navController = navController)
        }
        composable(AppScreens.HomeScreen.name) {
            HomeScreen(
                songViewModel = songViewModel,
                navController = navController,
                currentPlayingAudio = currentPlayingAudio,
                isAudioPlaying = isAudioPlaying,
                onStart = onStart,
                onNext = onNext,
                onPrevious = onPrevious
            )
        }
        composable(
            AppScreens.SongDetailScreen.name + "/{album}",
            arguments = listOf(navArgument(name = "album") { type = NavType.StringType })
        ) { backStackEntry ->
            SongDetailScreen(
                navController = navController,
                albumName = backStackEntry.arguments?.getString("album"),
                songViewModel = songViewModel,
                currentPlayingAudio = currentPlayingAudio,
                isAudioPlaying = isAudioPlaying,
                onStart = onStart,
                onItemClick = onItemClick,
                onNext = onNext,
                onPrevious = onPrevious
            )
        }
        composable(AppScreens.CurrentPlayingScreen.name) {
            CurrentPlayingScreen(
                navController = navController,
                currentPlayingSong = currentPlayingAudio,
                progress = progress,
                onProgress = onProgress,
                isAudioPlaying = isAudioPlaying,
                onStart = onStart,
                onNext = onNext,
                onPrevious = onPrevious
            )
        }
    }
}