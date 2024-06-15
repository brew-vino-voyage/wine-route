package com.brew.wine_route

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brew.wine_route.navigation.WineRouteNavHost
import com.brew.wine_route.ui.theme.WineRouteTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WineRouteTheme {
                WineRouteNavHost()
            }
        }
    }
}
