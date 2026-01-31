package com.amro.movies.data.repository

import com.amro.movies.data.api.MovieApi
import com.amro.movies.data.mapper.toDomain
import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.MovieDetail
import com.amro.movies.domain.model.TrendingMovie
import com.amro.movies.domain.repository.MovieRepository
import jakarta.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {
    override suspend fun getGenres(): List<Genre> {
        val response = api.genres()
        return response.genres.map { it.toDomain() }
    }

    override suspend fun getTrendingMovies(): List<TrendingMovie> {
        val genres = getGenres().associateBy { it.id }
        val response = api.trendingMovies()
        return response.results.map { it.toDomain(genres) }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        val response = api.movieDetails(movieId)
        return response.toDomain()
    }
}