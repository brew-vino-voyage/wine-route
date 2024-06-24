package com.brew.wine_route.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brew.wine_route.screen.LoginScreen
import com.brew.wine_route.screen.ResetPasswordScreen
import com.brew.wine_route.ui.WineRouteAppBar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun WineRouteNavHost() {
    val auth = Firebase.auth
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.fromRoute(
        backStackEntry?.destination?.route ?: Screen.Home.route
    )
    val startDestination = if (auth.currentUser != null) {
        Screen.Home.route
    } else {
        Screen.SignIn.route
    }

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
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(Screen.SignIn.route) {
                LoginScreen(
                    navController = navController
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    currentUser = auth.currentUser,
                    onSignOutClick = {
                        auth.signOut()
                        navController.popBackStack()
                        navController.navigate(Screen.SignIn.route)
                    }
                )
            }
            composable(Screen.ResetPassword.route) {
                ResetPasswordScreen(
                    navController = navController
                )
            }
            composable(Screen.WineryMap.route) {
                //                            WineryMapScreen()
            }
            composable(Screen.Profile.route) {
                //                            ProfileScreen()
            }
            composable(Screen.ProductListing.route) {
                //                            ProductListingScreen()
            }
            composable(Screen.Community.route) {
                //                            CommunityScreen()
            }
        }
    }
}


// TODO: 임시로 만든 홈 화면
@Composable
fun HomeScreen(
    currentUser: FirebaseUser?,
    onSignOutClick: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentUser != null) {
                Text(text = "이메일: ${currentUser.email}")
            } else {
                Text(text = "로그인 정보가 없습니다.")
            }
            Button(onClick = onSignOutClick) {
                Text(text = "로그아웃")
            }
        }
    }
}