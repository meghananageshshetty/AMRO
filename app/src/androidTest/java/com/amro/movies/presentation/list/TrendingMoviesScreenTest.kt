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

        private const val MOVIE_TITLE = "Movie_title"
        private const val MOVIE_ID = 1

        private val GENRE = Genre(1, "Genre")

        private const val POSTER_URL = "poster/url.jpg"
        private const val RELEASE_DATE = "2024-03-01"

        private const val POPULARITY = 99.0

        private val TRENDING_MOVIE = TrendingMovie(
            MOVIE_ID,
            MOVIE_TITLE,
            listOf(GENRE),
            POSTER_URL,
            RELEASE_DATE,
            POPULARITY
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
                        genres = listOf(GENRE)
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
        composeRule.onNodeWithText(GENRE.name).assertIsDisplayed()
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
                        genres = listOf(GENRE)
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
        assertEquals(1, clickedId)
    }
}
