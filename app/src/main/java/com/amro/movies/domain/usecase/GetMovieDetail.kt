package com.amro.movies.domain.usecase

import com.amro.movies.domain.model.MovieDetail
import com.amro.movies.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetail = repository.getMovieDetail(movieId)
}
