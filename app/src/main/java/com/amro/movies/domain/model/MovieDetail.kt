package com.amro.movies.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val tagline: String?,
    val overview: String?,
    val runtime: Int?,
    val status: String?,
    val budget: Long?,
    val genres: List<Genre>,
    val revenue: Long?,
    val voteAverage: Double,
    val voteCount: Int,
    val posterUrl: String?,
    val imdbUrl: String?,
    val releaseDate: String?
)
