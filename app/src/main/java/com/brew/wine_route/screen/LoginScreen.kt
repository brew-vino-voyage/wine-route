package com.brew.wine_route.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DesktopMac
import androidx.compose.material.icons.rounded.Laptop
import androidx.compose.material.icons.rounded.TabletAndroid
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    var clickLogin by remember { mutableStateOf(true) }
    var clickSignup by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidePassword by remember { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
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
        LoginTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = "이메일")
            },
            placeholder = {
                Text(text = "이메일을 입력하세요")
            },
            imageVector = Icons.Rounded.Close
        ) {
            email = ""
        }
        LoginTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = "비밀번호")
            },
            placeholder = {
                Text(text = "**********")
            },
            hidePassword = hidePassword,
            imageVector = if (hidePassword) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
        ) {
            hidePassword = !hidePassword
        }
        if (clickLogin) {
            GoNextButton(
                text = "로그인"
            ) {
                // click login button
            }
        } else {
            GoNextButton(
                text = "회원가입"
            ) {
                // click signup button
            }
        }
        if (clickLogin) {
            Button(
                onClick = {
                    // click forget password
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "비밀번호를 잊으셨나요?")
            }
        }
    }
    Text(
        text = "또는",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        SocialLoginButton(imageVector = Icons.Rounded.TabletAndroid) {

        }
        SocialLoginButton(imageVector = Icons.Rounded.Laptop) {

        }
        SocialLoginButton(imageVector = Icons.Rounded.DesktopMac) {

        }
    }
}


@Composable
fun SignChoiceButton(
    text: String,
    position: Float,
    clickButton: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(if (!clickButton) Color.Transparent else Color.Blue),
        modifier = Modifier
            .fillMaxWidth(position)
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
fun GoNextButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun SocialLoginButton(imageVector: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}