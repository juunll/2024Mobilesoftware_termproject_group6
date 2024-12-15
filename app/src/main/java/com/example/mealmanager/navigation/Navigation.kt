package com.example.mealmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mealmanager.ui.theme.analysis.MealAnalysisScreen
import com.example.mealmanager.ui.theme.display.MealDetailScreen
import com.example.mealmanager.ui.theme.display.MealListScreen
import com.example.mealmanager.ui.theme.home.HomeScreen
import com.example.mealmanager.ui.theme.input.MealInputScreen
import com.example.mealmanager.data.Meal
import com.example.mealmanager.data.MealRepository

@Composable
fun Navigation(navController: NavHostController, repository: MealRepository)
{
    NavHost(navController, startDestination = "home")
    {
        composable("home") { HomeScreen(navController) }
        composable("meal_input") { MealInputScreen(repository = repository) }
        composable("meal_list") {
            MealListScreen(navController = navController, repository = repository)
        }
        composable("meal_analysis") { MealAnalysisScreen(repository = repository) }

        // 상세보기 화면 경로 추가
        composable("meal_detail/{mealId}") { backStackEntry ->
            // mealId를 경로 파라미터에서 가져오기
            val mealId = backStackEntry.arguments?.getString("mealId")?.toIntOrNull() ?: -1 // String -> Int 변환
            val selectedMeal = if (mealId != -1) repository.getMealById(mealId) else null

            MealDetailScreen(
                meal = selectedMeal,
                onBack = { navController.popBackStack() }
            )
        }
    }
}