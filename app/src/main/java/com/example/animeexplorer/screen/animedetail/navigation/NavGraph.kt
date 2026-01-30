package com.example.animeexplorer.screen.animedetail.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.animeexplorer.screen.animedetail.AnimeDetailScreen
import com.example.animeexplorer.screen.animedetail.animelist.AnimeListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(navController, startDestination = "list") {

        composable("list") {
            AnimeListScreen(navController)
        }

        composable("detail/{id}") {
            AnimeDetailScreen(
                animeId = it.arguments!!.getString("id")!!.toInt()
            )
        }
    }
}
