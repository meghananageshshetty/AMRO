package com.amro.movies.domain.model

data class TrendingMovie(
    val id: Int,
    val title: String,
    val genres: List<Genre>,
    val posterUrl: String?,
    val releaseDate: String?,
    val popularity: Double
)