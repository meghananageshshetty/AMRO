package com.amro.movies.presentation.presentation.trendingmovies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.amro.movies.presentation.presentation.sortfilter.FilterSortRow


@Composable
fun TrendingMoviesRoute(
    onMovieClick: (Int) -> Unit,
    viewModel: TrendingMoviesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    TrendingMoviesList(
        state = state,
        onRetry = { viewModel.load() },
        onMovieClick = onMovieClick,
        onGenreSelected = viewModel::onGenreSelected,
        onSortOptionSelected = viewModel::onSortOptionSelected,
        onSortOrderSelected = viewModel::onSortOrderSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingMoviesList(
    state: TrendingMoviesState,
    onRetry: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onGenreSelected: (Int?) -> Unit,
    onSortOptionSelected: (SortOption) -> Unit,
    onSortOrderSelected: (SortOrder) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = "Trending movies this week",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )

            FilterSortRow(
                genres = state.genres,
                selectedGenreId = state.selectedGenreId,
                sortOption = state.sortOption,
                sortOrder = state.sortOrder,
                onGenreSelected = onGenreSelected,
                onSortOptionSelected = onSortOptionSelected,
                onSortOrderSelected = onSortOrderSelected
            )

            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.errorMessage,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetry) {
                            Text(text = "retry")
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.visibleMovies) { movie ->
                            MovieRow(movie = movie, onClick = { onMovieClick(movie.id) })
                        }
                        item { Spacer(modifier = Modifier.height(12.dp)) }
                    }
                }
            }
        }
    }
}

