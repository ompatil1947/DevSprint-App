package com.example.robodate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.robodate.ui.screens.MainScreen
import com.example.robodate.ui.theme.RoboDateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoboDateTheme {
                MainScreen()
            }
        }
    }
}
