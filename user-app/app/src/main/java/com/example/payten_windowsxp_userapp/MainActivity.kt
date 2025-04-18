package com.example.payten_windowsxp_userapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.payten_windowsxp_userapp.Navigation.ScreenManager
import com.example.payten_windowsxp_userapp.ui.theme.Payten_WindowsXP_UserAPPTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Payten_WindowsXP_UserAPPTheme {
                ScreenManager()
            }
        }
    }
}
