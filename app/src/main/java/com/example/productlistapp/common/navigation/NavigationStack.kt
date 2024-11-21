package com.example.productlistapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.productlistapp.presentation.screens.DetailScreen
import com.example.productlistapp.presentation.screens.ListingScreen

@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.List.route) {
        composable(route = Screen.List.route) {
            ListingScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            DetailScreen(id = it.arguments?.getString("id"))
        }
    }
}