package com.example.kpi_mobileappdev_lab5.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Calc2SharedViewModel: ViewModel() {
    // потік для збереження параметрів другого калькулятора у вигляді ключ-значення
    private val _parameters = MutableStateFlow(
        // linkedhashmap для збереження порядку
        linkedMapOf(
            "lossesEmergency" to "23.6",
            "lossesScheduled" to "17.6",
            "pm" to "5.12",
            "tm" to "6451.0",
            "failureRate" to "0.01",
            "averageRecoveryTime" to "0.045",
            "averagePlannedDowntime" to "0.004"
        )
    )
    // публічний доступ до параметрів другого калькулятора (тільки читання)
    val parameters = _parameters.asStateFlow()

    // функція оновлення параметрів другого калькулятора
    fun updateParameters(updatedParameters: LinkedHashMap<String, String>) {
        _parameters.value = updatedParameters
    }

    // функція розрахунку збитків, що спричинені перервами в електропостачанні
    fun evaluate(): LinkedHashMap<String, Int> {
        // приведення вхідних даних калькулятора до типу Double
        val numParameters: HashMap<String, Double> = HashMap()
        parameters.value.forEach { (key, value) ->
            numParameters[key] = value.toDouble()
        }

        // добуток Pm та Tm
        val commonOperand: Double = numParameters["pm"]!! * 1000 * numParameters["tm"]!!
        // математичне сподівання планового недовідпущення електроенергії
        val expectedOutagesScheduled = (numParameters["averagePlannedDowntime"]!! * commonOperand).toInt()
        // математичне сподівання аварійного недовідпущення електроенергії
        val expectedOutagesEmergency = (numParameters["failureRate"]!! *
                numParameters["averageRecoveryTime"]!! * commonOperand).toInt()
        // математичне сподівання збитків від переривання електропостачання
        val expectedLosses = (numParameters["lossesEmergency"]!! * expectedOutagesEmergency +
                numParameters["lossesScheduled"]!! * expectedOutagesScheduled).toInt()

        return linkedMapOf(
            "expectedOutagesScheduled" to expectedOutagesScheduled,
            "expectedOutagesEmergency" to expectedOutagesEmergency,
            "expectedLosses" to expectedLosses
        )
    }
}
