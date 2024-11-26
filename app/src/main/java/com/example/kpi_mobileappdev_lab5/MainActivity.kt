package com.example.kpi_mobileappdev_lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kpi_mobileappdev_lab5.navigation.AppNavHost
import com.example.kpi_mobileappdev_lab5.ui.theme.KPIMobileAppDevLab5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KPIMobileAppDevLab5Theme {
                AppNavHost()
            }
        }
    }
}
