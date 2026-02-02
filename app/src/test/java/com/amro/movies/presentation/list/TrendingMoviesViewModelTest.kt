package com.amro.movies.presentation.list


import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.MovieDetail
import com.amro.movies.domain.model.TrendingMovie
import com.amro.movies.domain.repository.MovieRepository
import com.amro.movies.domain.usecase.GetGenres
import com.amro.movies.domain.usecase.GetTrendingMovies
import com.amro.movies.presentation.presentation.trendingmovies.SortOption
import com.amro.movies.presentation.presentation.trendingmovies.SortOrder
import com.amro.movies.presentation.presentation.trendingmovies.TrendingMovieErrorType
import com.amro.movies.presentation.presentation.trendingmovies.TrendingMoviesViewModel
import com.amro.movies.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrendingMoviesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `filters and sorts movies`() = runTest {
        val action = Genre(1, "Action")
        val comedy = Genre(2, "Comedy")

        val movies = listOf(
            TrendingMovie(1, "Bravo", listOf(action,comedy), posterUrl = "/gbVwHl4YPSq6BcC92TQpe7qUTh6.jpg",
                releaseDate = "2026-01-28",
                popularity = 584.6058),
            TrendingMovie(2, "Alpha", listOf(action,comedy), posterUrl = "/gbVwHl4YPSq6BcC92TQpe7qUTh6.jpg",
                releaseDate = "2026-01-28",
                popularity = 584.6058),
            TrendingMovie(3, "Charlie", listOf(comedy), posterUrl = "/gbVwHl4YPSq6BcC92TQpe7qUTh6.jpg",
                releaseDate = "2026-01-28",
                popularity = 584.6058)
        )

        val repository = FakeMovieRepository(genres = listOf(action, comedy), movies = movies)
        val viewModel = TrendingMoviesViewModel(
            getTrendingMovies = GetTrendingMovies(repository),
            getGenres = GetGenres(repository)
        )

        advanceUntilIdle()

        viewModel.onGenreSelected(1)
        viewModel.onSortOptionSelected(SortOption.TITLE)
        viewModel.onSortOrderSelected(SortOrder.ASC)

        val titles = viewModel.state.value.visibleMovies.map { it.title }
        assertEquals(listOf("Alpha", "Bravo"), titles)
    }

    @Test
    fun `default sort is popularity descending`() = runTest {
        val action = Genre(1, "Action")

        val movies = listOf(
            TrendingMovie(1, "Bravo", listOf(action), posterUrl = "/gbVwHl4YPSq6BcC92TQpe7qUTh6.jpg",
                releaseDate = "2026-01-28",
                popularity = 1.0),
            TrendingMovie(2, "Alpha", listOf(action), posterUrl = "/gbVwHl4YPSq6BcC92TQpe7qUTh6.jpg",
                releaseDate = "2026-01-28",
                popularity = 99.0),
            TrendingMovie(3, "Charlie", listOf(action), posterUrl = "/gbVwHl4YPSq6BcC92TQpe7qUTh6.jpg",
                releaseDate = "2026-01-28",
                popularity = 50.0)
        )

        val repository = FakeMovieRepository(genres = listOf(action), movies = movies)
        val viewModel = TrendingMoviesViewModel(
            getTrendingMovies = GetTrendingMovies(repository),
            getGenres = GetGenres(repository)
        )

        advanceUntilIdle()

        val ids = viewModel.state.value.visibleMovies.map { it.id }
        assertEquals(listOf(2, 3, 1), ids)
    }

    @Test
    fun `shows no movies message when trending is empty`() = runTest {
        val repository = FakeMovieRepository(genres = emptyList(), movies = emptyList())
        val viewModel = TrendingMoviesViewModel(
            getTrendingMovies = GetTrendingMovies(repository),
            getGenres = GetGenres(repository)
        )

        advanceUntilIdle()

        assertEquals(TrendingMovieErrorType.TRENDING_MOVIES_NOT_FOUND, viewModel.state.value.errorType)
        assertTrue(viewModel.state.value.visibleMovies.isEmpty())
    }

    @Test
    fun `shows network error when both requests fail`() = runTest {
        val repository = FailingMovieRepository()
        val viewModel = TrendingMoviesViewModel(
            getTrendingMovies = GetTrendingMovies(repository),
            getGenres = GetGenres(repository)
        )

        advanceUntilIdle()

        assertEquals(
            TrendingMovieErrorType.NETWORK_ERROR,
            viewModel.state.value.errorType
        )
    }
}

private class FakeMovieRepository(
    private val genres: List<Genre>,
    private val movies: List<TrendingMovie>
) : MovieRepository {
    override suspend fun getGenres(): List<Genre> = genres
    override suspend fun getTrendingMovies(): List<TrendingMovie> = movies
    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        throw IllegalStateException("Not needed for this test")
    }
}

private class FailingMovieRepository : MovieRepository {
    override suspend fun getGenres(): List<Genre> = error("boom")
    override suspend fun getTrendingMovies(): List<TrendingMovie> = error("boom")
    override suspend fun getMovieDetail(movieId: Int): MovieDetail = error("boom")
}
