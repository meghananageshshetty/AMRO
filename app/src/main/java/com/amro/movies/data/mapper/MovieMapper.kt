package com.amro.movies.data.mapper

import com.amro.movies.data.dto.GenreDto
import com.amro.movies.data.dto.MovieDetailsDto
import com.amro.movies.data.dto.MovieDto
import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.MovieDetail
import com.amro.movies.domain.model.TrendingMovie


const val POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
fun GenreDto.toDomain(): Genre = Genre(id = id, name = name)

fun MovieDto.toDomain(genres: Map<Int, Genre>): TrendingMovie {
    val mappedGenres = genreIds.mapNotNull { genres[it] }
    val posterUrl = posterPath?.let { path -> "$POSTER_IMAGE_BASE_URL$path" }
    return TrendingMovie(
        id = id,
        title = title,
        posterUrl = posterUrl,
        releaseDate = releaseDate,
        genres = mappedGenres,
        popularity = popularity,
    )
}

fun MovieDetailsDto.toDomain(): MovieDetail {
    val posterUrl = posterPath.let { path -> "$POSTER_IMAGE_BASE_URL$path" }
    val imdbUrl = imdbId.let { "https://www.imdb.com/title/$it" }
    return MovieDetail(
        id = id,
        title = title,
        tagline = tagline.takeIf { it.isNotBlank() },
        overview = overview.takeIf { it.isNotBlank() },
        budget = budget,
        imdbUrl = imdbUrl,
        posterUrl = posterUrl,
        voteAverage = voteAverage,
        revenue = revenue,
        runtime = runtime,
        genres = genres.map { it.toDomain() },
        voteCount = voteCount,
        status = status,
        releaseDate = releaseDate
    )
}
