package com.amro.movies.presentation.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amro.movies.presentation.presentation.moviedetails.MovieDetailRoute
import com.amro.movies.presentation.presentation.trendingmovies.TrendingMoviesRoute

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
        composable("${Routes.MOVIE_DETAIL}/{movieId}") {
            MovieDetailRoute(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
