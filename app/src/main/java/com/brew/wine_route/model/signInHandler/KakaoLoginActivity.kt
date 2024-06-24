package com.brew.wine_route.model.signInHandler

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brew.wine_route.R
import com.brew.wine_route.screen.SocialLoginButton

// TODO: 카카오 로그인
@Composable
fun HandleSignInWithKakao(navController: NavController) {
    // 카카오 로그인
    SocialLoginButton(
        painterResource = R.drawable.kakaotalk_logo,
        color = Color(0xFFFFEB00),
        modifier = Modifier
            .size(40.dp)
            .padding(top = 2.dp, start = 1.dp)
    ) {

    }
}
