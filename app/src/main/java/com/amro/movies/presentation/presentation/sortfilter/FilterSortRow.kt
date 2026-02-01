package com.amro.movies.presentation.presentation.sortfilter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amro.movies.domain.model.Genre
import com.amro.movies.presentation.presentation.trendingmovies.SortOption
import com.amro.movies.presentation.presentation.trendingmovies.SortOrder
import com.amro.movies.ui.theme.dropdownOutlinedTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSortRow(
    genres: List<Genre>,
    selectedGenreId: Int?,
    sortOption: SortOption,
    sortOrder: SortOrder,
    onGenreSelected: (Int?) -> Unit,
    onSortOptionSelected: (SortOption) -> Unit,
    onSortOrderSelected: (SortOrder) -> Unit
) {
    var genreExpanded by remember { mutableStateOf(false) }
    var sortExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = genreExpanded,
            onExpandedChange = { genreExpanded = !genreExpanded },
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = genres.firstOrNull { it.id == selectedGenreId }?.name ?: "Genre",
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                maxLines = 1,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genreExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = dropdownOutlinedTextFieldColors(),
                shape = RoundedCornerShape(16.dp)
            )
            ExposedDropdownMenu(
                expanded = genreExpanded,
                onDismissRequest = { genreExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("All") },
                    onClick = {
                        onGenreSelected(null)
                        genreExpanded = false
                    }
                )
                genres.forEach { genre ->
                    DropdownMenuItem(
                        text = { Text(genre.name) },
                        onClick = {
                            onGenreSelected(genre.id)
                            genreExpanded = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = sortExpanded,
            onExpandedChange = { sortExpanded = !sortExpanded },
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = sortOption.label,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = dropdownOutlinedTextFieldColors(),
                shape = RoundedCornerShape(16.dp)
            )
            ExposedDropdownMenu(
                expanded = sortExpanded,
                onDismissRequest = { sortExpanded = false }
            ) {
                SortOption.entries.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label) },
                        onClick = {
                            onSortOptionSelected(option)
                            sortExpanded = false
                        }
                    )
                }
            }
        }

        SortOrderToggle(
            sortOrder = sortOrder,
            onSortOrderSelected = onSortOrderSelected,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
private fun SortOrderToggle(
    sortOrder: SortOrder,
    onSortOrderSelected: (SortOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier.fillMaxWidth()) {
        SegmentedButton(
            selected = sortOrder == SortOrder.ASC,
            onClick = { onSortOrderSelected(SortOrder.ASC) },
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            label = { Text("Asc", maxLines = 1) }
        )
        SegmentedButton(
            selected = sortOrder == SortOrder.DESC,
            onClick = { onSortOrderSelected(SortOrder.DESC) },
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            label = { Text("Des", maxLines = 1) }
        )
    }
}