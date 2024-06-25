package com.brew.wine_route.model.signInHandler

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.navigation.NavController
import com.brew.wine_route.R
import com.brew.wine_route.navigation.Screen
import com.brew.wine_route.screen.SocialLoginButton
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun HandleSignInWithGoogle(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    SocialLoginButton(
        painterResource = R.drawable.google_logo,
        onClick = {
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

private fun handleSignIn(result: GetCredentialResponse, navController: NavController) {
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

private fun handleFailure(e: GetCredentialException, navController: NavController) {
    if (e is NoCredentialException) {
        Log.e(TAG, "NoCredentialException", e)
        navController.navigate(Screen.SignIn.route)
    } else {
        Log.e(TAG, "Error getting credential", e)
    }
    navController.navigate(Screen.SignIn.route)
}
