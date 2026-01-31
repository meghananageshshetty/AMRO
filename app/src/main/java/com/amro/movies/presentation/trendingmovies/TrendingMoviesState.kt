package com.amro.movies.presentation.trendingmovies

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

data class TrendingMoviesState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val allMovies: List<TrendingMovie> = emptyList(),
    val visibleMovies: List<TrendingMovie> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val selectedGenreId: Int? = null,
    val sortOption: SortOption = SortOption.POPULARITY,
    val sortOrder: SortOrder = SortOrder.DESC
)
