package com.example.kpi_mobileappdev_lab5.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.kpi_mobileappdev_lab5.screens.Calc1InputScreen
import com.example.kpi_mobileappdev_lab5.screens.Calc1ResultScreen
import com.example.kpi_mobileappdev_lab5.viewmodels.Calc1SharedViewModel
import com.example.kpi_mobileappdev_lab5.screens.Calc2InputScreen
import com.example.kpi_mobileappdev_lab5.screens.Calc2ResultScreen
import com.example.kpi_mobileappdev_lab5.screens.CalcSelectionScreen
import com.example.kpi_mobileappdev_lab5.viewmodels.Calc2SharedViewModel

@Composable
fun AppNavHost() {
    // ініціалізація навігаційного контролера
    val navController = rememberNavController()

    // визначення маршрутизатора для навігації
    NavHost(
        navController = navController,
        startDestination = SelectionRoute
    ) {
        // екран вибору калькулятора
        composable(SelectionRoute) {
            CalcSelectionScreen(
                toCalc1InputScreen = { navController.navigate(Calc1Route) },
                toCalc2InputScreen = { navController.navigate(Calc2Route) }
            )
        }

        // сабграф для першого калькулятора
        navigation(
            route = Calc1Route,
            startDestination = Calc1InputRoute
        ) {
            // екран вводу параметрів калькулятора #1
            composable(Calc1InputRoute) {
                // ініціалізація viewmodel для роботи з вхідними даними калькулятора #1
                val sharedViewModel: Calc1SharedViewModel = viewModel(
                    remember (NavBackStackEntry) { navController.getBackStackEntry(Calc1Route) }
                )
                Calc1InputScreen(
                    sharedViewModel = sharedViewModel,
                    toCalc1ResultScreen = { navController.navigate(Calc1ResultRoute) },
                    toCalcSelection = {
                        // повернення до екрану вибору калькулятора
                        navController.navigate(SelectionRoute) {
                            popUpTo(Calc1Route) { inclusive = true }
                        }
                    }
                )
            }
            // екран результатів калькулятора #1
            composable(Calc1ResultRoute) {
                // ініціалізація viewmodel для роботи з вхідними даними калькулятора #1
                val sharedViewModel: Calc1SharedViewModel = viewModel(
                    remember (NavBackStackEntry) { navController.getBackStackEntry(Calc1Route) }
                )
                Calc1ResultScreen(
                    sharedViewModel = sharedViewModel,
                    toCalc1InputScreen = {
                        // повернення до екрану вводу параметрів калькулятора #1
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        }
                    }
                )
            }
        }

        // сабграф для другого калькулятора
        navigation(
            route = Calc2Route,
            startDestination = Calc2InputRoute
        ) {
            // екран вводу параметрів калькулятора #2
            composable(Calc2InputRoute) {
                // ініціалізація viewmodel для роботи з вхідними даними калькулятора #2
                val sharedViewModel: Calc2SharedViewModel = viewModel(
                    remember (NavBackStackEntry) { navController.getBackStackEntry(Calc2Route) }
                )
                Calc2InputScreen(
                    sharedViewModel = sharedViewModel,
                    toCalc2ResultScreen = { navController.navigate(Calc2ResultRoute) },
                    toCalcSelection = {
                        // повернення до екрану вибору калькулятора
                        navController.navigate(SelectionRoute) {
                            popUpTo(Calc2Route) { inclusive = true }
                        }
                    }
                )
            }
            // екран результатів калькулятора #2
            composable(Calc2ResultRoute) {
                // ініціалізація viewmodel для роботи з вхідними даними калькулятора #2
                val sharedViewModel: Calc2SharedViewModel = viewModel(
                    remember (NavBackStackEntry) { navController.getBackStackEntry(Calc2Route) }
                )
                Calc2ResultScreen(
                    sharedViewModel = sharedViewModel,
                    toCalc2InputScreen = {
                        // повернення до екрану вводу параметрів калькулятора #2
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}
