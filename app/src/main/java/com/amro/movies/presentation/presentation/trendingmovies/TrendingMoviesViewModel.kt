package com.amro.movies.presentation.presentation.trendingmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.movies.domain.model.TrendingMovie
import com.amro.movies.domain.usecase.GetGenres
import com.amro.movies.domain.usecase.GetTrendingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val getTrendingMovies: GetTrendingMovies,
    private val getGenres: GetGenres
) : ViewModel() {

    private val _state = MutableStateFlow(TrendingMoviesState())
    val state: StateFlow<TrendingMoviesState> = _state

    init {
        load()
    }

    fun load() {
        _state.update { it.copy(isLoading = true, errorType = null) }
        viewModelScope.launch {
            val genresResultDeferred = async { runCatching { getGenres() } }
            val moviesResultDeferred = async { runCatching { getTrendingMovies() } }

            val genresResult = genresResultDeferred.await()
            val moviesResult = moviesResultDeferred.await()

            val genres = genresResult.getOrElse { emptyList() }
            val movies = moviesResult.getOrElse { emptyList() }

            if (genresResult.isFailure && moviesResult.isFailure) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorType = TrendingMovieErrorType.NETWORK_ERROR,
                        genres = genres,
                        allMovies = emptyList(),
                        visibleMovies = emptyList()
                    )
                }
                return@launch
            }

            val updated = _state.value.copy(
                isLoading = false,
                errorType = if (movies.isEmpty()) TrendingMovieErrorType.TRENDING_MOVIES_NOT_FOUND else null,
                genres = genres,
                allMovies = movies
            )
            _state.value = updated.copy(visibleMovies = applyFilters(updated))
        }
    }

    fun onGenreSelected(genreId: Int?) {
        _state.update {
            val updated = it.copy(selectedGenreId = genreId)
            updated.copy(visibleMovies = applyFilters(updated))
        }
    }

    fun onSortOptionSelected(option: SortOption) {
        _state.update {
            val updated = it.copy(sortOption = option)
            updated.copy(visibleMovies = applyFilters(updated))
        }
    }

    fun onSortOrderSelected(order: SortOrder) {
        _state.update {
            val updated = it.copy(sortOrder = order)
            updated.copy(visibleMovies = applyFilters(updated))
        }
    }

    private fun applyFilters(state: TrendingMoviesState): List<TrendingMovie> {
        val filtered = state.allMovies.filter { movie ->
            state.selectedGenreId?.let { genreId ->
                movie.genres.any { it.id == genreId }
            } ?: true
        }

        val sorted = when (state.sortOption) {
            SortOption.POPULARITY -> filtered.sortedBy { it.popularity }
            SortOption.TITLE -> filtered.sortedBy { it.title.lowercase() }
            SortOption.RELEASE_DATE -> filtered.sortedBy { it.releaseDate ?: "" }
        }

        return if (state.sortOrder == SortOrder.DESC) sorted.reversed() else sorted
    }
}