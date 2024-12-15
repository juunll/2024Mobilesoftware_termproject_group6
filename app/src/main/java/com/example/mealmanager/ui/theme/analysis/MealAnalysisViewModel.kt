package com.example.mealmanager.ui.theme.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealmanager.data.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import android.util.Log

class MealAnalysisViewModel(private val repository: MealRepository) : ViewModel() {
    private val _analysisData = MutableStateFlow(AnalysisData())
    val analysisData: StateFlow<AnalysisData> = _analysisData

    fun loadMealsForMonth(month: String) {
        val mealsForMonth = repository.getMealsByMonth(month)

        val totalCalories = mealsForMonth.sumOf { meal ->
            repository.getCalorieForMeal(meal.foodName)
        }

        val costByMealType = mutableMapOf(
            "조식" to 0,
            "중식" to 0,
            "석식" to 0,
            "간식/음료" to 0
        )
        mealsForMonth.forEach { meal ->
            costByMealType[meal.mealType] = costByMealType[meal.mealType]?.plus(meal.cost) ?: meal.cost
        }

        _analysisData.value = AnalysisData(
            totalCalories = totalCalories,
            costByMealType = costByMealType
        )
    }
}

data class AnalysisData(
    val totalCalories: Int = 0,
    val costByMealType: Map<String, Int> = emptyMap()
)



class MealAnalysisViewModelFactory(private val repository: MealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealAnalysisViewModel::class.java)) {
            return MealAnalysisViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
