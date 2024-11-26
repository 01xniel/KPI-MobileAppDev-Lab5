package com.example.kpi_mobileappdev_lab5.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.kpi_mobileappdev_lab5.styledcomponents.BodyText
import com.example.kpi_mobileappdev_lab5.styledcomponents.HeaderText

@Composable
fun CalcSelectionScreen(
    toCalc1InputScreen: () -> Unit,
    toCalc2InputScreen: () -> Unit
) {
    // головний контейнер екрану для вибору калькулятора
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedButton(
            onClick = toCalc1InputScreen,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 18.dp)
            ) {
                HeaderText("Завдання #1")
                BodyText(
                    text = AnnotatedString("Порівняння надійності одноколової та " +
                            "двоколової систем"),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = toCalc2InputScreen,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 18.dp)
            ) {
                HeaderText("Завдання #2")
                BodyText(
                    text = AnnotatedString("Розрахунок збитків, що спричинені " +
                            "перервами в електропостачанні"),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
