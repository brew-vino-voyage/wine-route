package com.brew.wine_route.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.brew.wine_route.R
import com.brew.wine_route.model.signInHandler.HandleSignInWithFacebook
import com.brew.wine_route.model.signInHandler.HandleSignInWithGoogle
import com.brew.wine_route.model.signInHandler.TwitterLogin
import com.brew.wine_route.navigation.Screen
import com.brew.wine_route.viewModel.SignInViewModel
import com.brew.wine_route.viewModel.SignInViewModelFactory

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val twitterLogin = remember { TwitterLogin(context) }
    val factory = remember { SignInViewModelFactory(twitterLogin) }
    val signInViewModel: SignInViewModel = viewModel(factory = factory)

    var clickLogin by remember { mutableStateOf(true) }
    var clickSignup by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidePassword by remember { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 로그인/회원가입 토글 버튼
            SignChoiceButton(
                text = "로그인",
                position = 0.5f,
                clickButton = clickLogin,
            ) {
                clickLogin = true
                clickSignup = false
            }
            SignChoiceButton(
                text = "회원가입",
                position = 1f,
                clickButton = clickSignup,
            ) {
                clickLogin = false
                clickSignup = true
            }
        }
        // 이메일, 비밀번호 입력
        LoginTextField(value = email,
            onValueChange = { email = it },
            label = { Text(text = "이메일") },
            placeholder = { Text(text = "이메일을 입력하세요") },
            imageVector = Icons.Rounded.Close
        ) {
            email = ""
        }
        LoginTextField(value = password,
            onValueChange = { password = it },
            label = { Text(text = "비밀번호") },
            placeholder = { Text(text = "**********") },
            hidePassword = hidePassword,
            imageVector = if (hidePassword) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
        ) {
            hidePassword = !hidePassword
        }
        // 로그인하거나 회원가입하는 버튼
        if (clickLogin) {
            PerformLoginButton(text = "로그인", onClick = {
                signInViewModel.signIn(email,
                    password,
                    onSuccess = { navController.navigate(Screen.Home.route) },
                    onFailure = { exception ->
                        Toast.makeText(
                            context,
                            "Authentication failed: ${exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
            })
        } else {
            PerformLoginButton(text = "회원가입", onClick = {
                signInViewModel.createAccount(email,
                        password,
                        onSuccess = { navController.navigate(Screen.SignIn.route) },
                        onFailure = { exception ->
                            Toast.makeText(
                                context,
                                "Account creation failed: ${exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
            })
        }
        // 비밀번호를 잊었을 때 재설정하는 화면으로 이동하는 버튼
        if (clickLogin) {
            Button(
                onClick = { navController.navigate("resetPassword") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "비밀번호를 잊으셨나요?")
            }
        }
        Text(
            text = "또는", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
        ) {
            HandleSignInWithGoogle(navController = navController)
            HandleSignInWithFacebook(navController = navController)
            HandleSignInWithTwitter(signInViewModel, navController)
            // 카카오로그인
        }
    }
}

@Composable
private fun HandleSignInWithTwitter(
    signInViewModel: SignInViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    SocialLoginButton(
        painterResource = R.drawable.twitter_logo_lightmode,
        onClick = {
            signInViewModel.signInWithTwitter(
                onSuccess = {
                    navController.navigate(Screen.Home.route)
                },
                onError = { errorMessage ->
                    Toast.makeText(
                        context,
                        "Twitter Authentication failed: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    )
}


@Composable
fun SignChoiceButton(
    text: String, position: Float, clickButton: Boolean, onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(if (!clickButton) Color.Transparent else Color.Blue),
        modifier = Modifier.fillMaxWidth(position)
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    hidePassword: Boolean = false,
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        keyboardOptions = if (value == "password" && hidePassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        visualTransformation = if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
        maxLines = 1,
        trailingIcon = {
            IconButton(onClick = onClick) {
                Icon(imageVector = imageVector, contentDescription = "null")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun PerformLoginButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun SocialLoginButton(
    modifier: Modifier = Modifier,
    painterResource: Int,
    color: Color = Color.Transparent,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(color),
        modifier = Modifier
            .size(40.dp)
            .padding(0.dp)
    ) {
        Image(
            painter = painterResource(id = painterResource),
            contentDescription = null,
            modifier = modifier
        )
    }
}

// 비밀번호 재설정하는 화면
@Composable
fun ResetPasswordScreen(navController: NavHostController) {
    val signInViewModel: SignInViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = email,
                onValueChange = { email = it },
                label = { Text(text = "이메일") },
                placeholder = { Text(text = "이메일을 입력하세요") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Button(
                onClick = {
                    signInViewModel.resetPassword(email,
                        onSuccess = { navController.navigate(Screen.SignIn.route) },
                        onFailure = { exception ->
                            Toast.makeText(
                                context,
                                "Password reset failed: ${exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                }, modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = "비밀번호 재설정하기")
            }
        }
    }
}