package com.brew.wine_route.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.brew.wine_route.model.signInHandler.TwitterLogin
import com.brew.wine_route.model.signInHandler.TwitterUserInfo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val twitterLogin: TwitterLogin) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userInfo = MutableStateFlow<TwitterUserInfo?>(null)
    val userInfo: StateFlow<TwitterUserInfo?> = _userInfo

    fun signInWithTwitter(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = twitterLogin.signIn()
            result.fold(
                onSuccess = { userInfo ->
                    _userInfo.value = userInfo
                    onSuccess()
                },
                onFailure = { error -> onError(error.message ?: "Unknown error occurred") }
            )
        }
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    onSuccess()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    onFailure(task.exception)
                }
            }
    }

    fun createAccount(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    onSuccess()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    onFailure(task.exception)
                }
            }
    }

    fun resetPassword(email: String, onSuccess: () -> Unit, onFailure: (Exception?) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    onSuccess()
                } else {
                    onFailure(task.exception)
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }

    companion object {
        private const val TAG = "SignInViewModel"
    }
}

class ViewModelFactory(private val twitterLogin: TwitterLogin) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(twitterLogin) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}