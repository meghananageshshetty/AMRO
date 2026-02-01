package com.amro.movies.presentation.presentation.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.movies.domain.usecase.GetMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<String>("movieId")?.toIntOrNull() ?: -1

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state

    init {
        load()
    }

    fun load() {
        if (movieId == -1) {
            _state.update { it.copy(errorType = MovieDetailErrorType.MOVIE_ID_INVALID) }
            return
        }
        _state.update { it.copy(isLoading = true, errorType = null) }
        viewModelScope.launch {
            runCatching { getMovieDetail(movieId) }
                .onSuccess { movie ->
                    _state.update { it.copy(isLoading = false, movie = movie) }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorType = MovieDetailErrorType.MOVIE_DETAIL_NOT_FOUND,
                        )
                    }
                }
        }
    }
}
