package com.amro.movies.domain.usecase

import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenres @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Genre> = repository.getGenres()
}
