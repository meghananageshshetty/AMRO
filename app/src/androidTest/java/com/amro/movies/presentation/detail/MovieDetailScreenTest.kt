package com.amro.movies.presentation.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
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

        private const val MOVIE_TITLE = "Movie_title"

        private const val MOVIE_TAGLINE = "Movie_tagline"

        private const val MOVIE_OVERVIEW = "Movie_overview"
        private const val MOVIE_ID = 1

        private val GENRE = Genre(1, "Genre")

        private const val POSTER_URL = "poster/url.jpg"
        private const val RELEASE_DATE = "2024-03-01"


        private val MOVIE_DETAIL = MovieDetail(
            MOVIE_ID,
            MOVIE_TITLE,
            MOVIE_TAGLINE,
            MOVIE_OVERVIEW,
            3,
            "Released",
            1000000,
            listOf(GENRE),
            70000,
            8.0,
            8,
            POSTER_URL,
            "imdb/url",
            RELEASE_DATE

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
        composeRule.onNodeWithText(GENRE.name).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.rating)).assertIsDisplayed()

        composeRule.onNodeWithText(context.getString(R.string.budget)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.revenue)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.runtime)).assertIsDisplayed()
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
