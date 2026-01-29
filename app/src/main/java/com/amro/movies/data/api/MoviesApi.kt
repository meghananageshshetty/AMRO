package com.amro.movies.data.api

import com.amro.movies.data.dto.GenresDto
import com.amro.movies.data.dto.MovieDetailsDto
import com.amro.movies.data.dto.TrendingMoviesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("trending/movie/{timeWindow}")
    suspend fun trendingMovies(
        @Path("time_window") timeWindow: String = "week"
    ): TrendingMoviesDto

    @GET("movie/{movieId}")
    suspend fun movieDetails(
        @Path("movie_id") movieId: Int,
    ): MovieDetailsDto

    @GET("genre/movie/list")
    suspend fun genres(
    ): GenresDto

}