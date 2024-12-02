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
import com.example.kpi_mobileappdev_lab5.styledcomponents.BodyText
import com.example.kpi_mobileappdev_lab5.styledcomponents.ButtonText
import com.example.kpi_mobileappdev_lab5.styledcomponents.CustomButton
import com.example.kpi_mobileappdev_lab5.styledcomponents.HeaderText
import com.example.kpi_mobileappdev_lab5.viewmodels.Calc1SharedViewModel
import java.util.Locale

@Composable
fun Calc1ResultScreen(
    sharedViewModel: Calc1SharedViewModel,
    toCalc1InputScreen: () -> Unit
) {
    // результати розрахунку частот відмов одноколової та двоколової систем
    val results = sharedViewModel.evaluate()

    // текстові описи кожного зі значень отриманого результату
    val resultsDescriptions = hashMapOf(
        "failureRateSCS" to "Частота відмов одноколової системи",
        "averageRecoveryTime" to "Середня тривалість відновлення одноколової системи",
        "coefEmergencyDowntimeSCS" to "Коефіцієнт аварійного простою одноколової системи",
        "coefScheduledDowntimeSCS" to "Коефіцієнт планового простою одноколової системи",
        "failureRateTCS" to "Частота відмов двоколової системи (не враховуючи секційний вимикач)",
        "failureRateWithSectionalizerTCS" to "Частота відмов двоколової системи (враховуючи секційний вимикач)"
    )

    // одиниці вимірювання кожного зі значень отриманого результату
    val resultsUnits = hashMapOf(
        "failureRateSCS" to "рік⁻¹",
        "averageRecoveryTime" to "год.",
        "coefEmergencyDowntimeSCS" to "",
        "coefScheduledDowntimeSCS" to "",
        "failureRateTCS" to "рік⁻¹",
        "failureRateWithSectionalizerTCS" to "рік⁻¹"
    )

    // головний контейнер екрану для виведення результатів розрахунку частот відмов
    // одноколової та двоколової систем
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
                    append("${resultsDescriptions[key]} складає: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append(
                        if (value > 0.1) {
                            String.format(Locale.US, "%.3f", value)
                        } else {
                            String.format(Locale.US, "%.2E", value)
                        }
                    )
                    append(" ${resultsUnits[key]}")
                    pop()
                }.toAnnotatedString(),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            onClickAction = toCalc1InputScreen,
            buttonContent = { ButtonText(text = "Назад") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}
