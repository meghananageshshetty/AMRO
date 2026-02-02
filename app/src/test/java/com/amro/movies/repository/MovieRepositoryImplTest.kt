package com.amro.movies.repository

import com.amro.movies.data.api.MovieApi
import com.amro.movies.data.dto.GenreDto
import com.amro.movies.data.dto.GenresDto
import com.amro.movies.data.dto.MovieDetailsDto
import com.amro.movies.data.dto.MovieDto
import com.amro.movies.data.dto.TrendingMoviesDto
import com.amro.movies.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    @Test
    fun `getTrendingMovies fetches 5 pages and returns top 100`() = runTest {
        val api = FakeMovieApi()
        val repository = MovieRepositoryImpl(api)

        val movies = repository.getTrendingMovies()

        assertEquals(listOf(1, 2, 3, 4, 5), api.requestedPages.sorted())
        assertEquals(100, movies.size)
        assertEquals(1, movies.first().id)
        assertEquals(100, movies.last().id)
        assertEquals("Action", movies.first().genres.first().name)
    }

    @Test
    fun `getTrendingMovies with single page failure and keeps successful results`() = runTest {
        val api = FakeMovieApi(failingPages = setOf(3))
        val repository = MovieRepositoryImpl(api)

        val movies = repository.getTrendingMovies()

        assertEquals(80, movies.size)
        assertTrue(movies.none { it.id in 41..60 })
        assertEquals(listOf(1, 2, 3, 4, 5), api.requestedPages.sorted())
    }

    @Test
    fun `genres are cached and fetched once`() = runTest {
        val api = FakeMovieApi()
        val repository = MovieRepositoryImpl(api)

        repository.getGenres()
        repository.getGenres()
        repository.getTrendingMovies()

        assertEquals(1, api.genreRequestCount)
    }
}

private class FakeMovieApi(
    private val failingPages: Set<Int> = emptySet()
) : MovieApi {
    val requestedPages = mutableListOf<Int>()
    var genreRequestCount = 0

    override suspend fun trendingMovies(
        timeWindow: String,
        page: Int
    ): TrendingMoviesDto {
        requestedPages += page
        if (page in failingPages) error("Page failed")

        val startId = ((page - 1) * 20) + 1
        val movies = (startId until startId + 20).map { id ->
            MovieDto(
                id = id,
                title = "Movie $id",
                genreIds = listOf(1),
                posterPath = "/poster_$id.jpg",
                releaseDate = "2024-01-01",
                popularity = id.toDouble()
            )
        }
        return TrendingMoviesDto(results = movies)
    }

    override suspend fun movieDetails(movieId: Int): MovieDetailsDto {
        return MovieDetailsDto(
            id = movieId,
            title = "Movie $movieId",
            genres = listOf(GenreDto(id = 1, name = "Action")),
            posterPath = "/poster_$movieId.jpg",
            releaseDate = "2024-01-01",
            overview = "Overview",
            imdbId = "tt1234567",
            voteAverage = 9.9,
            voteCount = 1000,
            tagline = "Tagline",
            budget = 1000000,
            revenue = 2000000,
            runtime = 120,
            status = "Released"
        )
    }


    override suspend fun genres(): GenresDto {
        genreRequestCount += 1
        return GenresDto(
            genres = listOf(GenreDto(id = 1, name = "Action"))
        )
    }
}
