package com.amro.movies.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.MovieDetail
import com.amro.movies.domain.model.TrendingMovie
import com.amro.movies.domain.repository.MovieRepository
import com.amro.movies.domain.usecase.GetMovieDetail
import com.amro.movies.presentation.presentation.moviedetails.MovieDetailErrorType
import com.amro.movies.presentation.presentation.moviedetails.MovieDetailViewModel
import com.amro.movies.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    companion object {
        private const val MOVIE_TITLE = "The Wrecking Crew"
        private const val MOVIE_TAGLINE = "Estranged half-brothers reunite"
        private const val MOVIE_OVERVIEW =
            "Estranged half-brothers Jonny and James reunite after their father's mysterious death. " +
                    "As they search for the truth, buried secrets reveal a conspiracy threatening to tear their family apart."
        private val GENRE_ACTION = Genre(28, "Action")
        private val GENRE_COMEDY = Genre(35, "Comedy")
        private val GENRE_CRIME = Genre(80, "Crime")
        private val GENRE_MYSTERY = Genre(9648, "Mystery")

        private val MOVIE_DETAIL = MovieDetail(
            id = 1168190,
            title = MOVIE_TITLE,
            tagline = MOVIE_TAGLINE,
            overview = MOVIE_OVERVIEW,
            runtime = 120,
            status = "Released",
            budget = 1000000,
            genres = listOf(
                GENRE_ACTION,
                GENRE_COMEDY,
                GENRE_CRIME,
                GENRE_MYSTERY
            ),
            revenue = 7000000,
            voteAverage = 6.424,
            voteCount = 138,
            posterUrl = "/gbVwHl4YPSq6BcC92TQpe7qUTh6.jpg",
            imdbUrl = "https://www.imdb.com/title/tt1168190/",
            releaseDate = "2026-01-28"
        )
    }

    @Test
    fun `loads movie detail successfully`() = runTest {
        val repository = FakeMovieDetailRepository(MOVIE_DETAIL)

        val viewModel = MovieDetailViewModel(
            getMovieDetail = GetMovieDetail(repository),
            savedStateHandle = SavedStateHandle(mapOf("movieId" to "1168190"))
        )

        advanceUntilIdle()

        assertEquals(null, viewModel.state.value.errorType)
        assertEquals(MOVIE_TITLE, viewModel.state.value.movie?.title)
    }

    @Test
    fun `shows error when movie id is invalid`() = runTest {
        val repository = FakeMovieDetailRepository(detail = MOVIE_DETAIL)

        val viewModel = MovieDetailViewModel(
            getMovieDetail = GetMovieDetail(repository),
            savedStateHandle = SavedStateHandle(mapOf("movieId" to "abc"))
        )

        advanceUntilIdle()

        assertEquals(MovieDetailErrorType.MOVIE_ID_INVALID, viewModel.state.value.errorType)
    }

    @Test
    fun `shows error when detail request fails`() = runTest {
        val repository = FakeMovieDetailRepository(shouldFail = true)

        val viewModel = MovieDetailViewModel(
            getMovieDetail = GetMovieDetail(repository),
            savedStateHandle = SavedStateHandle(mapOf("movieId" to "99"))
        )

        advanceUntilIdle()

        assertEquals(MovieDetailErrorType.MOVIE_DETAIL_NOT_FOUND, viewModel.state.value.errorType)
        assertEquals(null, viewModel.state.value.movie)
    }
}

private class FakeMovieDetailRepository(
    private val detail: MovieDetail? = null,
    private val shouldFail: Boolean = false
) : MovieRepository {
    override suspend fun getGenres(): List<Genre> = emptyList()
    override suspend fun getTrendingMovies(): List<TrendingMovie> = emptyList()

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        if (shouldFail) error("boom")
        return requireNotNull(detail)
    }
}
