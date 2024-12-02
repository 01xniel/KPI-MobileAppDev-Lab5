package com.example.kpi_mobileappdev_lab5.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Calc1SharedViewModel: ViewModel() {
    // mutablestateflow для збереження параметрів першого калькулятора
    private val _parameters = MutableStateFlow(
        linkedMapOf(
            "pl110Q" to "10",
            "pl35Q" to "0",
            "pl10Q" to "0",
            "kl10TrenchQ" to "0",
            "kl10CableQ" to "0",
            "t110Q" to "1",
            "t35Q" to "0",
            "t10CableNetworkQ" to "0",
            "t10AirQ" to "0",
            "v110Q" to "1",
            "v10LowOilQ" to "1",
            "v10VacuumQ" to "0",
            "busBar10Q" to "6",
            "av038Q" to "0",
            "ed610Q" to "0",
            "ed038Q" to "0"
        )
    )
    // stateflow для доступу до значень параметрів першого калькулятора
    // без можливості безпосередньо їх змінювати
    val parameters = _parameters.asStateFlow()

    // функція оновлення значень параметрів першого калькулятора
    fun updateParameters(updatedParameters: LinkedHashMap<String, String>) {
        _parameters.value = updatedParameters
    }

    // функція розрахунку частот відмов одноколової та двоколової систем
    fun evaluate(): LinkedHashMap<String, Double> {
        // клас для зберігання даних показників надійності
        data class ReliabilityIndicatorData(
            val failureRate: Double,
            val recoveryTime: Double,
            val repairFrequency: Double,
            val currentRepairDuration: Int? = null
        )

        // визначення показників надійності ліній електропередачі та електрообладнання підстанцій
        val reliabilityIndicators: HashMap<String, ReliabilityIndicatorData> = hashMapOf(
            "pl110Q" to ReliabilityIndicatorData(0.007, 10.0, 0.167, 35),
            "pl35Q" to ReliabilityIndicatorData(0.02, 8.0, 0.167, 35),
            "pl10Q" to ReliabilityIndicatorData(0.02, 10.0, 0.167, 35),
            "kl10TrenchQ" to ReliabilityIndicatorData(0.03, 44.0, 1.0, 9),
            "kl10CableQ" to ReliabilityIndicatorData(0.005, 17.5, 1.0, 9),
            "t110Q" to ReliabilityIndicatorData(0.015, 100.0, 1.0, 43),
            "t35Q" to ReliabilityIndicatorData(0.02, 80.0, 1.0, 28),
            "t10CableNetworkQ" to ReliabilityIndicatorData(0.005, 60.0, 0.5, 10),
            "t10AirQ" to ReliabilityIndicatorData(0.05, 60.0, 0.5, 10),
            "v110Q" to ReliabilityIndicatorData(0.01, 30.0, 0.1, 30),
            "v10LowOilQ" to ReliabilityIndicatorData(0.02, 15.0, 0.33, 15),
            "v10VacuumQ" to ReliabilityIndicatorData(0.01, 15.0, 0.33, 15),
            "busBar10Q" to ReliabilityIndicatorData(0.03, 2.0, 0.167, 5),
            "av038Q" to ReliabilityIndicatorData(0.05, 4.0, 0.33, 10),
            "ed610Q" to ReliabilityIndicatorData(0.1, 160.0, 0.5),
            "ed038Q" to ReliabilityIndicatorData(0.1, 50.0, 0.5)
        )

        // приведення вхідних даних калькулятора до типу Int
        val numParameters: HashMap<String, Int> = HashMap()
        parameters.value.forEach { (key, value) ->
            numParameters[key] = value.toInt()
        }

        // частота відмов одноколової системи
        var failureRateSCS = 0.0
        // середня тривалість відновлення
        var averageRecoveryTime = 0.0
        numParameters.forEach { (key, value) ->
            if (value != 0) {
                val element = reliabilityIndicators[key]!!.failureRate * value
                failureRateSCS += element
                averageRecoveryTime += element * reliabilityIndicators[key]!!.recoveryTime
            }
        }
        averageRecoveryTime /= failureRateSCS
        // коефіцієнт аварійного простою одноколової системи
        val coefEmergencyDowntimeSCS = (failureRateSCS * averageRecoveryTime) / 8760
        // коефіцієнт планового простою одноколової системи
        val coefScheduledDowntimeSCS = 1.2 * 43 / 8760
        // частота відмов одночасно двох кіл двоколової системи
        val failureRateTCS = 2 * failureRateSCS * (coefEmergencyDowntimeSCS + coefScheduledDowntimeSCS)
        // частота відмов двоколової системи з урахуванням секційного вимикача
        val failureRateWithSectionalizerTCS = failureRateTCS + 0.02

        return linkedMapOf(
            "failureRateSCS" to failureRateSCS,
            "averageRecoveryTime" to averageRecoveryTime,
            "coefEmergencyDowntimeSCS" to coefEmergencyDowntimeSCS,
            "coefScheduledDowntimeSCS" to coefScheduledDowntimeSCS,
            "failureRateTCS" to failureRateTCS,
            "failureRateWithSectionalizerTCS" to failureRateWithSectionalizerTCS
        )
    }
}
