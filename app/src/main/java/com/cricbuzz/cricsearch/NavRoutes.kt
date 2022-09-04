package com.cricbuzz.cricsearch

sealed class NavRoutes(val route: String) {
    object MainScreen : NavRoutes("home")
    object DetailScreen : NavRoutes("detail")
}