package com.example.kpi_mobileappdev_lab5.styledcomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// компонент для відображення тексту кнопки
@Composable
fun ButtonText(text: String) {
    Text(
        text,
        fontSize = 16.sp,
        lineHeight = 25.sp,
        modifier = Modifier.padding(vertical = 15.5.dp)
    )
}
