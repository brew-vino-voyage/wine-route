package com.brew.wine_route.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.getString
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brew.wine_route.R
import com.brew.wine_route.model.firebaseAuth.handleFailure
import com.brew.wine_route.model.firebaseAuth.handleSignIn
import com.brew.wine_route.ui.WineRouteAppBar
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

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
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                    onSignInClick = {
                        val credentialManager = CredentialManager.create(context)

                        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                            .setFilterByAuthorizedAccounts(false)
                            .setServerClientId(getString(context, R.string.web_client_id))
                            .setAutoSelectEnabled(true)
                            .build()

                        val request: GetCredentialRequest = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOption)
                            .build()

                        coroutineScope.launch {
                            try {
                                val result = credentialManager.getCredential(
                                    request = request,
                                    context = context,
                                )
                                handleSignIn(result, navController)
                            } catch (e: GetCredentialException) {
                                handleFailure(e, navController)
                            }
                        }
                    }
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

// TODO: 임시로 만든 로그인 화면
@Composable
fun LoginScreen(onSignInClick: () -> Unit) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_icon_light),
                contentDescription = "Google Sign In",
                modifier = Modifier.clickable(onClick = onSignInClick)
            )
        }
    }
}
