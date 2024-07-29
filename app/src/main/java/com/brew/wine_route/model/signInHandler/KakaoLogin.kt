package com.brew.wine_route.model.signInHandler


import android.app.Application
import com.brew.wine_route.R
import com.kakao.sdk.common.KakaoSdk


class KakaoLogin : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, R.string.kakao_app_native_key.toString())
    }
}