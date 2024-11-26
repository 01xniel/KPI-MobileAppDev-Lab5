package com.example.kpi_mobileappdev_lab5.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kpi_mobileappdev_lab5.viewmodels.Calc2SharedViewModel
import com.example.kpi_mobileappdev_lab5.styledcomponents.BodyText
import com.example.kpi_mobileappdev_lab5.styledcomponents.ButtonText
import com.example.kpi_mobileappdev_lab5.styledcomponents.CustomButton
import com.example.kpi_mobileappdev_lab5.styledcomponents.HeaderText

@Composable
fun Calc2ResultScreen(
    sharedViewModel: Calc2SharedViewModel,
    toCalc2InputScreen: () -> Unit
) {
    // результати розрахунку збитків, що спричинені перервами в електропостачанні
    val results = sharedViewModel.evaluate()

    // hashmap, що зберігає текстові описи кожного зі значень отриманого результату
    val resultsDescriptions = hashMapOf(
        "expectedOutagesScheduled" to "Очікується, що планове недовідпущення електроенергії складе",
        "expectedOutagesEmergency" to "Очікується, що аварійне недовідпущення електроенергії складе",
        "expectedLosses" to "Очікуване значення збитків від перерв електропостачання"
    )

    // hashmap, що зберігає одиниці вимірювання кожного зі значень отриманого результату
    val resultsUnits = hashMapOf(
        "expectedOutagesScheduled" to "кВт⋅год.",
        "expectedOutagesEmergency" to "кВт⋅год.",
        "expectedLosses" to "грн."
    )

    // головний контейнер екрану для виведення результатів розрахунку збитків, що
    // спричинені перервами в електропостачанні
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
    ) {
        HeaderText(
            text = "Результати",
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        results.forEach { (key, value) ->
            BodyText(
                text = AnnotatedString.Builder().apply {
                    append("${resultsDescriptions[key]}: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("$value ${resultsUnits[key]}")
                    pop()
                }.toAnnotatedString(),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            onClickAction = toCalc2InputScreen,
            buttonContent = { ButtonText(text = "Назад") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}
