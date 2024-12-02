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
import com.example.kpi_mobileappdev_lab5.viewmodels.Calc1SharedViewModel
import com.example.kpi_mobileappdev_lab5.styledcomponents.BodyText
import com.example.kpi_mobileappdev_lab5.styledcomponents.ButtonText
import com.example.kpi_mobileappdev_lab5.styledcomponents.CustomButton
import com.example.kpi_mobileappdev_lab5.styledcomponents.CustomTextField
import com.example.kpi_mobileappdev_lab5.styledcomponents.HeaderText

@Composable
fun Calc1InputScreen(
    sharedViewModel: Calc1SharedViewModel,
    toCalc1ResultScreen: () -> Unit,
    toCalcSelection: () -> Unit
) {
    // отримання параметрів першого калькулятора з sharedViewModel
    val parameters by sharedViewModel.parameters.collectAsStateWithLifecycle()

    // назви параметрів
    val textFieldLabels = hashMapOf(
        "pl110Q" to "ПЛ-110 кВ",
        "pl35Q" to "ПЛ-35 кВ",
        "pl10Q" to "ПЛ-10 кВ",
        "kl10TrenchQ" to "КЛ-10 кВ (траншея)",
        "kl10CableQ" to "КЛ-10 кВ (кабельний канал)",
        "t110Q" to "Т-110 кВ",
        "t35Q" to "Т-35 кВ",
        "t10CableNetworkQ" to "Т-10 кВ (кабельна мережа 10 кВ)",
        "t10AirQ" to "Т-10 кВ (повітряна мережа 10 кВ)",
        "v110Q" to "В-110 кВ (елегазовий)",
        "v10LowOilQ" to "В-10 кВ (малооливний)",
        "v10VacuumQ" to "В-10 кВ (вакуумний)",
        "busBar10Q" to "Збірні шини 10 кВ на 1 приєднання",
        "av038Q" to "АВ-0.38 кВ",
        "ed610Q" to "ЕД-6.10 кВ",
        "ed038Q" to "ЕД-0.38 кВ"
    )

    // поточний контекст застосунку
    val context = LocalContext.current

    // головний контейнер екрану для введення параметрів калькулятора #1
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 96.dp)
    ) {
        HeaderText(
            text = "Завдання #1",
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        BodyText(
            text = AnnotatedString("Калькулятор для порівняння " +
                    "надійності одноколової та двоколової систем електропередачі"),
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
                        if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
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
                        toCalc1ResultScreen()
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
