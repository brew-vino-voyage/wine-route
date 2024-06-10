package com.brew.wine_route.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.brew.wine_route.R
import org.checkerframework.checker.units.qual.C

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
            SignChoiceButton(text = "로그인", position = 0.5f)
            SignChoiceButton(text = "회원가입", position = 1f)
        }
        TextFieldLine(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = "이메일을 입력하세요")
            },
            imageVector = com.facebook.login.R.drawable.com_facebook_close
        ) {
            // onClick for IconButton
        }
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
fun SignChoiceButton(text: String, position: Float) {
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

@Composable
fun TextFieldLine(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    imageVector: Int,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        LoginTextField(
            value = value,
            onValueChange = onValueChange,
            label = label
        )
        IconButton(onClick = onClick) {
            Icon(imageVector = ImageVector.vectorResource(id = imageVector), contentDescription = "null")
        }
    }
}