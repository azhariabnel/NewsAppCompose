package com.example.newsappcompose.navigation

sealed class NewsScreenNavigation(val route : String) {
    data object SplashScreen : NewsScreenNavigation("splash")
    data object HomeScreen : NewsScreenNavigation("home")
    data object DetailScreen : NewsScreenNavigation("detail")
}