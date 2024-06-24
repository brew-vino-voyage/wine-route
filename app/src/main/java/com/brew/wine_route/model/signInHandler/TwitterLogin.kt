package com.brew.wine_route.model.signInHandler

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

data class TwitterUserInfo(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?
)

class TwitterLogin(private val context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signIn(): Result<TwitterUserInfo> {
        return try {
            val provider = OAuthProvider.newBuilder("twitter.com")
            provider.addCustomParameter("lang", "ko")  // 언어 설정 (선택사항)

            // 이메일과 프로필 사진 요청
            val scopes = listOf("email", "profile")
            provider.scopes = scopes

            val pendingResultTask = auth.pendingAuthResult
            val result = if (pendingResultTask != null) {
                pendingResultTask.await()
            } else {
                auth.startActivityForSignInWithProvider(context as Activity, provider.build()).await()
            }

            val user = result.user
            if (user != null) {
                Result.success(getUserInfo(user))
            } else {
                Result.failure(Exception("Failed to get user information"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getUserInfo(user: FirebaseUser): TwitterUserInfo {
        return TwitterUserInfo(
            uid = user.uid,
            displayName = user.displayName,
            email = user.email,
            photoUrl = user.photoUrl?.toString()
        )
    }
}