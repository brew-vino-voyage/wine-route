package com.brew.wine_route.model.signInHandler


import android.app.Application
import com.kakao.sdk.common.KakaoSdk


class KakaoLogin : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "4e66d0e4c6f06cbd956a6d246b00c9a5")
    }
}