package com.amro.movies.presentation.presentation.trendingmovies

import com.amro.movies.R
import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.TrendingMovie

enum class SortOption(val label: String) {
    POPULARITY("Popularity"),
    TITLE("Title"),
    RELEASE_DATE("Release Date")
}

enum class SortOrder(val label: String) {
    DESC("Descending"),
    ASC("Ascending")
}

enum class TrendingMovieErrorType(val errorMessage: Int) {
    NETWORK_ERROR(R.string.network_error_loading_movies),
    TRENDING_MOVIES_NOT_FOUND(R.string.error_loading_trending_movies)
}

data class TrendingMoviesState(
    val isLoading: Boolean = false,
    val errorType: TrendingMovieErrorType? = null,
    val allMovies: List<TrendingMovie> = emptyList(),
    val visibleMovies: List<TrendingMovie> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val selectedGenreId: Int? = null,
    val sortOption: SortOption = SortOption.POPULARITY,
    val sortOrder: SortOrder = SortOrder.DESC
)
