package com.amro.movies.data.repository

import com.amro.movies.data.api.MovieApi
import com.amro.movies.data.mapper.toDomain
import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.MovieDetail
import com.amro.movies.domain.model.TrendingMovie
import com.amro.movies.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.async


class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    private var cachedGenres: List<Genre>? = null
    override suspend fun getGenres(): List<Genre> {
        cachedGenres?.let { return it }
        val response = api.genres()
        val genres = response.genres.map { it.toDomain() }
        cachedGenres = genres
        return genres
    }

    override suspend fun getTrendingMovies(): List<TrendingMovie> {
        val genres = getGenres().associateBy { it.id }

        val results = kotlinx.coroutines.supervisorScope {
            // Launch 5 requests in parallel; if one fails, continue with others
            val deferredPages = (1..5).map { page ->
                async {
                    try {
                        api.trendingMovies(page = page).results
                    } catch (e: Exception) {
                        emptyList()
                    }
                }
            }

            // Await all requests and merge into a single list
            deferredPages.flatMap { it.await() }
        }

        return results
            //fetch top 100
            .take(100)
            .map { it.toDomain(genres) }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        val response = api.movieDetails(movieId)
        return response.toDomain()
    }
}