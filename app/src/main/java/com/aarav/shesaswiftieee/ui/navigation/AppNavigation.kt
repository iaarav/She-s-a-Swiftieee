package com.aarav.shesaswiftieee.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aarav.shesaswiftieee.ui.viewModel.song.SongViewModel
import com.aarav.shesaswiftieee.ui.screens.musicPlayerScreen.CurrentPlayingScreen
import com.aarav.shesaswiftieee.ui.screens.homeScreen.HomeScreen
import com.aarav.shesaswiftieee.ui.screens.songDetailScreen.SongDetailScreen
import com.aarav.shesaswiftieee.ui.viewModel.musicPlayer.MusicViewModel
import com.aarav.shesaswiftieee.ui.viewModel.shared.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun AppNavigation(
    songViewModel: SongViewModel,
    sharedViewModel: SharedViewModel,
    musicViewModel: MusicViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = AppScreens.HomeScreen.name
    ) {

        composable(AppScreens.HomeScreen.name) {
            HomeScreen(
                songViewModel = songViewModel,
                navController = navController,
                onEvent = songViewModel::onEvent,
                musicPlaybackUiState = sharedViewModel.musicPlaybackState,
                onNavigateToMusicPlayer = {
                    navController.navigate(AppScreens.MusicPlayerScreen.name)
                }
            )
        }
        composable(
            AppScreens.SongDetailScreen.name + "/{album}",
            arguments = listOf(navArgument(name = "album") { type = NavType.StringType })
        ) { backStackEntry ->
            SongDetailScreen(
                albumName = backStackEntry.arguments?.getString("album"),
                songVM = songViewModel,
                onEvent = songViewModel::onEvent,
                homeUiState = songViewModel.homeUiState,
                musicPlaybackUiState = sharedViewModel.musicPlaybackState,
                onNavigateToMusicPlayer = {
                    navController.navigate(AppScreens.MusicPlayerScreen.name)
                }
            )

        }
        composable(
            route = AppScreens.MusicPlayerScreen.name,
            enterTransition = {
                expandVertically(
                    animationSpec = tween(300),
                    expandFrom = Alignment.Top
                )
            },
            exitTransition = {
                shrinkVertically(
                    animationSpec = tween(300),
                    shrinkTowards = Alignment.Top
                )
            }
        ) {
            CurrentPlayingScreen(
                onNavigateUp = { navController.navigateUp() },
                onEvent = musicViewModel::onEvent,
                musicPlaybackUIState = sharedViewModel.musicPlaybackState
            )
        }
    }
}