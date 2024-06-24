package com.brew.wine_route.model.signInHandler

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.brew.wine_route.R
import com.brew.wine_route.screen.SocialLoginButton

// TODO: 트위터 로그인
@Composable
fun HandleSignInWithX(navController: NavController) {
    SocialLoginButton(
        painterResource = R.drawable.twitter_logo_lightmode
    ) {

    }
}


