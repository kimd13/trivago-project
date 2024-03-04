package com.kimd13.starwarssampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kimd13.design.theme.StarWarsTheme
import com.kimd13.starwarssampleapp.navigation.StarWarsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarWarsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsTheme {
                StarWarsNavHost()
            }
        }
    }
}