package com.aarav.shesaswiftieee.navigation

enum class AppScreens {
    HomeScreen, SongDetailScreen, SplashScreen, CurrentPlayingScreen;

    companion object {
        fun fromRoute(route: String?): AppScreens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            SongDetailScreen.name -> SongDetailScreen
            CurrentPlayingScreen.name -> CurrentPlayingScreen
            SplashScreen.name -> SplashScreen
            null -> HomeScreen
            else -> throw IllegalAccessException("ROUTE $route NOT FOUND")
        }
    }

}