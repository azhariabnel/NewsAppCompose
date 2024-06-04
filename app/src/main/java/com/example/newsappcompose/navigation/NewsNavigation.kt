package com.example.newsappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsappcompose.screens.DetailScreen
import com.example.newsappcompose.screens.FadeOutSplash
import com.example.newsappcompose.screens.HomeScreen
import com.example.newsappcompose.viewmodel.NewsViewModel

@Composable
fun NewsNavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<NewsViewModel>()
    NavHost(navController = navController,
        startDestination = NewsScreenNavigation.SplashScreen.route){
        composable(NewsScreenNavigation.SplashScreen.route){
            FadeOutSplash(navController = navController)
        }
        composable(NewsScreenNavigation.HomeScreen.route){
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(NewsScreenNavigation.DetailScreen.route){
            DetailScreen(navController = navController, viewModel = viewModel)
        }
    }
}