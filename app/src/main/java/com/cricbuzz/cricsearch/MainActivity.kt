package com.cricbuzz.cricsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cricbuzz.cricsearch.ui.components.screens.DetailScreen
import com.cricbuzz.cricsearch.ui.components.screens.MainScreen
import com.cricbuzz.cricsearch.ui.theme.CricSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CricSearchTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.MainScreen.route,
                ) {
                    composable(NavRoutes.MainScreen.route) {
                        MainScreen(navController)
                    }
                    composable(NavRoutes.DetailScreen.route+ "/{name}") {
                            backStackEntry ->
                        val restaurantName = backStackEntry.arguments?.getString("name")
                        DetailScreen(restaurantName)
                    }
                }
            }
        }
    }
}
