package com.example.kpi_mobileappdev_lab5.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kpi_mobileappdev_lab5.viewmodels.Calc2SharedViewModel
import com.example.kpi_mobileappdev_lab5.styledcomponents.BodyText
import com.example.kpi_mobileappdev_lab5.styledcomponents.ButtonText
import com.example.kpi_mobileappdev_lab5.styledcomponents.CustomButton
import com.example.kpi_mobileappdev_lab5.styledcomponents.CustomTextField
import com.example.kpi_mobileappdev_lab5.styledcomponents.HeaderText

// функція перевірки валідності введеного значення в текстовому полі
fun isNewValueValid(value: String): Boolean {
    return if (value.isEmpty()) {
        true
    } else if (value.all { it.isDigit() || it == '.' }) {
        value.count { it == '.' } < 2
    } else {
        false
    }
}

@Composable
fun Calc2InputScreen(
    sharedViewModel: Calc2SharedViewModel,
    toCalc2ResultScreen: () -> Unit,
    toCalcSelection: () -> Unit
) {
    // змінна для зберігання поточного стану параметрів другого калькулятора
    val parameters by sharedViewModel.parameters.collectAsStateWithLifecycle()

    // hashmap для зберігання міток текстових полів другого калькулятора у вигляді ключ-значення
    val textFieldLabels = hashMapOf(
        "lossesEmergency" to "Збитки (аварійні відключення)",
        "lossesScheduled" to "Збитки (планові відключення)",
        "pm" to "Pm",
        "tm" to "Tm",
        "failureRate" to "Частота відмов",
        "averageRecoveryTime" to "Середній час відновлення",
        "averagePlannedDowntime" to "Середній час планового простою",
    )

    // отримання поточного контексту
    val context = LocalContext.current

    // головний контейнер екрану для введення значень калькулятора #2
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 64.dp)
    ) {
        HeaderText(
            text = "Завдання #2",
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        BodyText(
            text = AnnotatedString("Калькулятор розрахунку збитків від перерв " +
                    "електропостачання у разі застосування однотрансформаторної ГПП"),
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column (
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            parameters.forEach { (key, value) ->
                CustomTextField(
                    textFieldLabel = textFieldLabels[key]!!,
                    textFieldValue = value,
                    updateTextFieldValue = { newValue ->
                        if (isNewValueValid(newValue)) {
                            val newParameters = LinkedHashMap(parameters)
                            newParameters[key] = newValue
                            sharedViewModel.updateParameters(newParameters)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            CustomButton(
                onClickAction = toCalcSelection,
                buttonContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            )
            CustomButton(
                onClickAction = {
                    if (parameters.values.all { value -> value.isNotEmpty() }) {
                        // якщо всі параметри заповнені, то здійснюється
                        // перехід до сторінки з результатами
                        toCalc2ResultScreen()
                    } else {
                        // якщо хоча б один параметр порожній, то виводиться
                        // відповідне повідомлення
                        Toast.makeText(
                            context,
                            "Усі параметри мають бути заповнені",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                buttonContent = { ButtonText(text = "Розрахувати") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
