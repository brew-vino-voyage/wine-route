package com.brew.wine_route.model.signInHandler

import android.content.ContentValues.TAG
import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.navigation.NavController
import com.brew.wine_route.navigation.Screen
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


fun handleSignIn(result: GetCredentialResponse, navController: NavController) {
    val auth = Firebase.auth
    when (val credential = result.credential) {
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithCredential:success")
                            navController.navigate(Screen.Home.route)
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                        }
                    }
            }
        }
    }
}

fun handleFailure(e: GetCredentialException, navController: NavController) {
    if (e is NoCredentialException) {
        Log.e(TAG, "NoCredentialException", e)
        navController.navigate(Screen.SignIn.route)
    } else {
        Log.e(TAG, "Error getting credential", e)
    }
    navController.navigate(Screen.SignIn.route)
}
