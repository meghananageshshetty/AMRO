package com.amro.movies.presentation.trendingmovies

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

object Routes {
    const val TRENDING_MOVIE = "trending_movie"
    const val MOVIE_DETAIL = "movie_detail"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.TRENDING_MOVIE) {
        composable(Routes.TRENDING_MOVIE) {
            TrendingMoviesRoute(
                onMovieClick = { movieId ->
                    navController.navigate("${Routes.MOVIE_DETAIL}/$movieId")
                }
            )
        }
      //TODO add detail screen route
    }
}
