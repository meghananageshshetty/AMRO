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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amro.movies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationDrawer(onHomeClick: () -> Unit) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.menu_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        Divider()

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.user_profile_title)) },
            selected = false,
            onClick = onHomeClick,
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

    }
}