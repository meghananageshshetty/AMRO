package com.amro.movies.presentation.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationDrawer(onHomeClick: () -> Unit) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        Divider()

        NavigationDrawerItem(
            label = { Text("User Profile") },
            selected = false,
            onClick = onHomeClick,
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

    }
}