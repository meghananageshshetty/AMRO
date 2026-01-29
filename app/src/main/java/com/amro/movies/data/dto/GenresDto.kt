package com.amro.movies.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenresDto(
    val genres: List<GenreDto>
)

@JsonClass(generateAdapter = true)
data class GenreDto(
    val id: Int,
    val name: String
)