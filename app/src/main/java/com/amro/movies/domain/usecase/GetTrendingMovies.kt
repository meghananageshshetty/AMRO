package com.amro.movies.domain.usecase

import com.amro.movies.domain.model.TrendingMovie
import com.amro.movies.domain.repository.MovieRepository
import javax.inject.Inject

class GetTrendingMovies @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<TrendingMovie> = repository.getTrendingMovies()
}
