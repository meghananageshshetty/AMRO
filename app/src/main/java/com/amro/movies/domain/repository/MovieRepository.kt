package com.amro.movies.domain.repository

import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.TrendingMovie
import com.amro.movies.domain.model.MovieDetail

interface MovieRepository {
    suspend fun getGenres(): List<Genre>
    suspend fun getTrendingMovies(): List<TrendingMovie>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
}
