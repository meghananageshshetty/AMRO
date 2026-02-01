package com.amro.movies.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val runtime: Int,
    val status: String,
    val budget: Long,
    val genres: List<GenreDto>,
    val revenue: Long,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "imdb_id") val imdbId: String,
    @Json(name = "release_date") val releaseDate: String

)