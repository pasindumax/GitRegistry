package com.pasindu.gitregistry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pasindu.gitregistry.ui.screens.MainScreen
import com.pasindu.gitregistry.ui.theme.AppNewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNewTheme{MainScreen()}
            }

    }
}

