package com.amro.movies.presentation.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.amro.movies.R
import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.TrendingMovie
import com.amro.movies.presentation.presentation.trendingmovies.TrendingMovieErrorType
import com.amro.movies.presentation.presentation.trendingmovies.TrendingMoviesList
import com.amro.movies.presentation.presentation.trendingmovies.TrendingMoviesState
import com.amro.movies.ui.theme.AMROTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class TrendingMoviesScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    companion object {
        private const val MOVIE_TITLE = "The Wrecking Crew"
        private val GENRE_ACTION = Genre(28, "Action")
        private val GENRE_COMEDY = Genre(35, "Comedy")
        private val GENRE_CRIME = Genre(80, "Crime")
        private val GENRE_MYSTERY = Genre(9648, "Mystery")

        private val TRENDING_MOVIE = TrendingMovie(
            id = 1168190,
            title = MOVIE_TITLE,
            genres = listOf(
                GENRE_ACTION,
                GENRE_COMEDY,
                GENRE_CRIME,
                GENRE_MYSTERY
            ),
            posterUrl = "/gbVwHl4YPSq6BcC92TQpe7qUTh6.jpg",
            releaseDate = "2026-01-28",
            popularity = 584.6058
        )
    }

    @Test
    fun shows_trending_movie() {
        val movies = listOf(
            TRENDING_MOVIE
        )

        composeRule.setContent {
            AMROTheme() {
                TrendingMoviesList(
                    state = TrendingMoviesState(
                        isLoading = false,
                        errorType = null,
                        allMovies = movies,
                        visibleMovies = movies,
                        genres = listOf(GENRE_ACTION)
                    ),
                    onRetry = {},
                    onMovieClick = {},
                    onGenreSelected = {},
                    onSortOptionSelected = {},
                    onSortOrderSelected = {}
                )
            }
        }
        composeRule.onNodeWithTag("POSTER_IMAGE",useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText(MOVIE_TITLE).assertIsDisplayed()
        composeRule.onNodeWithText(listOfNotNull(TRENDING_MOVIE.releaseDate?.take(4), TRENDING_MOVIE.genres.joinToString { it.name }.takeIf { it.isNotBlank() }).joinToString(" • "),).assertIsDisplayed()
    }

    @Test
    fun shows_loading_state() {
        composeRule.setContent {
            AMROTheme() {
                TrendingMoviesList(
                    state = TrendingMoviesState(isLoading = true),
                    onRetry = {},
                    onMovieClick = {},
                    onGenreSelected = {},
                    onSortOptionSelected = {},
                    onSortOrderSelected = {}
                )
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.onNodeWithTag(context.getString(R.string.loading)).assertIsDisplayed()
    }

    @Test
    fun shows_error_then_retry() {
        var retryCount = 0
        composeRule.setContent {
            AMROTheme() {
                TrendingMoviesList(
                    state = TrendingMoviesState(
                        isLoading = false,
                        errorType = TrendingMovieErrorType.NETWORK_ERROR
                    ),
                    onRetry = { retryCount++ },
                    onMovieClick = {},
                    onGenreSelected = {},
                    onSortOptionSelected = {},
                    onSortOrderSelected = {}
                )
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.onNodeWithText(context.getString(R.string.network_error_loading_movies))
            .assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.retry)).performClick()
        assertEquals(1, retryCount)
    }

    @Test
    fun click_movie_navigates_to_detail() {
        val movies = listOf(
            TRENDING_MOVIE
        )
        var clickedId = -1

        composeRule.setContent {
            AMROTheme() {
                TrendingMoviesList(
                    state = TrendingMoviesState(
                        isLoading = false,
                        errorType = null,
                        allMovies = movies,
                        visibleMovies = movies,
                        genres = listOf(GENRE_ACTION)
                    ),
                    onRetry = {},
                    onMovieClick = { clickedId = it },
                    onGenreSelected = {},
                    onSortOptionSelected = {},
                    onSortOrderSelected = {}
                )
            }
        }

        composeRule.onNodeWithText(MOVIE_TITLE).performClick()
        assertEquals(TRENDING_MOVIE.id, clickedId)
    }
}
