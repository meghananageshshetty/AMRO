package com.amro.movies.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrendingMoviesDto(
    val results: List<MovieDto>
)

@JsonClass(generateAdapter = true)
data class MovieDto(
    val id: Int,
    val title: String,
    @field:Json(name = "genre_ids") val genreIds: List<Int>,
    @field:Json(name = "poster_path") val poster: String?,
    @field:Json(name = "release_date") val releaseDate: String
)