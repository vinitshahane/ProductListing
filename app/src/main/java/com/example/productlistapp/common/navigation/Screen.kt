package com.example.productlistapp.common.navigation

sealed class Screen(val route: String) {
    data object List: Screen("list_screen")
    data object Detail: Screen("detail_screen")
}
