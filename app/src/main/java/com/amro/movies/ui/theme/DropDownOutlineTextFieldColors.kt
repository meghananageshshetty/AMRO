package com.amro.movies.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropdownOutlinedTextFieldColors(): TextFieldColors {
    return ExposedDropdownMenuDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface
    )
}