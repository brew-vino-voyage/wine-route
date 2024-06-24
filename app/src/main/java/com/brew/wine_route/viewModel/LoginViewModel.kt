package com.brew.wine_route.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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