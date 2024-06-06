package com.brew.wine_route.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.brew.wine_route.R
import com.brew.wine_route.navigation.WineRouteScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WineRouteAppBar(
    currentScreen: WineRouteScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.navigate_up)
                    )
                }
            }
        }
    )
}