package com.example.kpi_mobileappdev_lab5.styledcomponents

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// компонент для відображення кнопки
@Composable
fun CustomButton(
    onClickAction: () -> Unit,
    buttonContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Button (
        onClick = onClickAction,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) { buttonContent() }
}
