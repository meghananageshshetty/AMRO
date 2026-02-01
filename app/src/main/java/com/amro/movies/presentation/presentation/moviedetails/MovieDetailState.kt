package com.amro.movies.presentation.presentation.moviedetails

import com.amro.movies.domain.model.MovieDetail


data class MovieDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movie: MovieDetail? = null
)
