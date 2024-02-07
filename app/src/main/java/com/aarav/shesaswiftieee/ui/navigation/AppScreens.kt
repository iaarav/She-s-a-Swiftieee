package com.aarav.shesaswiftieee.ui.navigation

enum class AppScreens {
    HomeScreen, SongDetailScreen, MusicPlayerScreen;

    companion object {
        fun fromRoute(route: String?): AppScreens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            SongDetailScreen.name -> SongDetailScreen
            MusicPlayerScreen.name -> MusicPlayerScreen

            null -> HomeScreen
            else -> throw IllegalAccessException("ROUTE $route NOT FOUND")
        }
    }

}