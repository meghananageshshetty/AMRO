package com.amro.movies.presentation.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.platform.app.InstrumentationRegistry
import com.amro.movies.R
import com.amro.movies.domain.model.Genre
import com.amro.movies.domain.model.MovieDetail
import com.amro.movies.presentation.presentation.moviedetails.MovieDetailErrorType
import com.amro.movies.presentation.presentation.moviedetails.MovieDetailScreen
import com.amro.movies.presentation.presentation.moviedetails.MovieDetailState
import com.amro.movies.ui.theme.AMROTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MovieDetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

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
    fun show_movie_details_content() {
        composeRule.setContent {
            AMROTheme {
                MovieDetailScreen(
                    state = MovieDetailState(isLoading = false, movie = MOVIE_DETAIL),
                    onRetry = {},
                    onBack = {}
                )
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.onNodeWithTag("POSTER_IMAGE", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText(MOVIE_TITLE).assertIsDisplayed()
        composeRule.onNodeWithText(MOVIE_TAGLINE).assertIsDisplayed()
        composeRule.onNodeWithText(MOVIE_OVERVIEW).assertIsDisplayed()
        composeRule.onNodeWithText(GENRE_ACTION.name).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.rating)).assertIsDisplayed()

        composeRule.onNodeWithText(context.getString(R.string.budget)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.revenue)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.runtime)).assertIsDisplayed()

        composeRule.onNodeWithTag("DETAILS_LIST")
            .performScrollToNode(hasText(context.getString(R.string.release_date)))
        composeRule.onNodeWithText(context.getString(R.string.release_date)).assertIsDisplayed()

        composeRule.onNodeWithTag("DETAILS_LIST")
            .performScrollToNode(hasTestTag("IMDB"))
        composeRule.onNodeWithTag("IMDB").assertIsDisplayed()


    }

    @Test
    fun show_error_then_retry() {
        var retryCount = 0

        composeRule.setContent {
            AMROTheme {
                MovieDetailScreen(
                    state = MovieDetailState(
                        isLoading = false,
                        errorType = MovieDetailErrorType.MOVIE_DETAIL_NOT_FOUND
                    ),
                    onRetry = { retryCount++ },
                    onBack = {}
                )
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.onNodeWithText(context.getString(R.string.error_loading_movie_details))
            .assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.retry)).performClick()
        assertEquals(1, retryCount)
    }

    @Test
    fun back_button_triggers_callback() {
        var backCount = 0

        composeRule.setContent {
            AMROTheme {
                MovieDetailScreen(
                    state = MovieDetailState(isLoading = false, movie = null),
                    onRetry = {},
                    onBack = { backCount++ }
                )
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithContentDescription(context.getString(R.string.back))
            .performClick()
        assertEquals(1, backCount)
    }
}
