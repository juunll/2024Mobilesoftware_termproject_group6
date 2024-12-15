package com.example.mealmanager.ui.theme.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealmanager.data.MealRepository

class MealInputViewModelFactory(
    private val repository: MealRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealInputViewModel::class.java)) {
            return MealInputViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
