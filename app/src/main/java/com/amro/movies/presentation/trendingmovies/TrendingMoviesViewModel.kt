package com.amro.movies.presentation.trendingmovies

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
        _state.update { it.copy(isLoading = true, errorMessage = null) }
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
                        errorMessage = "Unable to load movies. Please check your connection.",
                        genres = genres,
                        allMovies = emptyList(),
                        visibleMovies = emptyList()
                    )
                }
                return@launch
            }

            val updated = _state.value.copy(
                isLoading = false,
                errorMessage = if (movies.isEmpty()) "No movies found." else null,
                genres = genres,
                visibleMovies = movies,
                allMovies = movies
            )
            _state.value = updated.copy()
        }
    }

    fun onGenreSelected(genreId: Int?) {
        //TODO add genre selection logic
    }

    fun onSortOptionSelected(option: SortOption) {
        //TODO add sort option implementation

    }

    fun onSortOrderSelected(order: SortOrder) {
        //TODO add sort order implementation
    }

    private fun applyFilters(state: TrendingMoviesState): List<TrendingMovie> {
        return TODO("Provide the return value")
    }
}
