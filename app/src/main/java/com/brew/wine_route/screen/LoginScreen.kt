package com.brew.wine_route.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton(text = "로그인", position = 0.5f)
            ChoiceButton(text = "회원가입", position = 1f)
        }
        LoginTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = "이메일을 입력하세요")
            }
        )
        LoginTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = "**********")
            }
        )
    }
}

@Composable
fun ChoiceButton(text: String, position: Float) {
    Button(
        onClick = {  },
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
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        keyboardOptions = if (value == "password") KeyboardOptions(keyboardType = KeyboardType.NumberPassword) else KeyboardOptions.Default
    )
}