package com.example.findmyshows.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.findmyshows.ui.screens.MainScreen
import com.example.findmyshows.ui.theme.FindMyShowsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindMyShowsTheme {
                setContent{
                    MainScreen()
                }
            }
        }
    }
}