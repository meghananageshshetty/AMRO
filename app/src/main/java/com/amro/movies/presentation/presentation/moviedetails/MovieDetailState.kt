package com.amro.movies.presentation.presentation.moviedetails

import com.amro.movies.R
import com.amro.movies.domain.model.MovieDetail


data class MovieDetailState(
    val isLoading: Boolean = false,
    val errorType: MovieDetailErrorType? = null,
    val movie: MovieDetail? = null
)

enum class MovieDetailErrorType(val errorMessage: Int) {
    MOVIE_ID_INVALID(R.string.error_movie_id_invalid),
    MOVIE_DETAIL_NOT_FOUND(R.string.error_loading_movie_details),
    MISSING_API_KEY(R.string.missing_api_key)
}


