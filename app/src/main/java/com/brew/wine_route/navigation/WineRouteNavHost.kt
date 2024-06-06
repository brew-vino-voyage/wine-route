package com.brew.wine_route.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brew.wine_route.ui.WineRouteAppBar

@Composable
fun WineRouteNavHost() {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = WineRouteScreen.valueOf(
        backStackEntry?.destination?.route ?: WineRouteScreen.HOME.name
    )

    Scaffold(
        topBar = {
            WineRouteAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        }
    ) { innerPadding ->
        androidx.navigation.compose.NavHost(
            navController = navController,
            startDestination = WineRouteScreen.HOME.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(WineRouteScreen.SIGN_IN.name) {
                //                            SignInScreen()
            }
            composable(WineRouteScreen.SIGN_UP.name) {
                //                            SignUpScreen()
            }
            composable(WineRouteScreen.HOME.name) {
                //                            HomeScreen()
            }
            composable(WineRouteScreen.WINERY_MAP.name) {
                //                            WineryMapScreen()
            }
            composable(WineRouteScreen.PROFILE.name) {
                //                            ProfileScreen()
            }
            composable(WineRouteScreen.PRODUCT_LISTING.name) {
                //                            ProductListingScreen()
            }
            composable(WineRouteScreen.COMMUNITY.name) {
                //                            CommunityScreen()
            }
        }
    }
}