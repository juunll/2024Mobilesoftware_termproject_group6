package com.example.mealmanager.ui.theme.display

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mealmanager.data.Meal
import com.example.mealmanager.data.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModelProvider

class MealListViewModelFactory(
    private val repository: MealRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealListViewModel::class.java)) {
            return MealListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MealListViewModel(private val repository: MealRepository) : ViewModel() {
    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    init {
        loadAllMeals()
    }

    private fun loadAllMeals() {
        _meals.value = repository.getAllMeals()
    }

    private val _selectedMeal = MutableStateFlow<Meal?>(null) // 선택된 Meal 상태
    val selectedMeal: StateFlow<Meal?> = _selectedMeal

    fun loadMealsByDate(date: String) {
        _meals.value = repository.getMealsByDate(date)
    }

    fun selectMeal(meal: Meal) {
        _selectedMeal.value = meal // Meal 선택
    }

    fun getMealById(mealId: Int): Meal {
        return repository.getMealById(mealId) ?: throw IllegalArgumentException("Meal not found")
    }

    fun selectMeal(meal: Meal, navController: NavController) {
        navController.navigate("meal_detail/${meal.id}") // 화면 전환
    }
}


