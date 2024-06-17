package com.brew.wine_route.model.signInHandler

import androidx.activity.ComponentActivity


class XLoginActivity : ComponentActivity(), LoginProcessStarter {
    override fun startLoginProcess() {
        // X 로그인 프로세스 시작
        // 텍스트필드에서 값을 입력받으면 그 값을 이용해 로그인 프로세스를 시작하기
        // X 로그인 프로세스는 별도의 로그인 API를 사용하는 경우에 사용
    }
}