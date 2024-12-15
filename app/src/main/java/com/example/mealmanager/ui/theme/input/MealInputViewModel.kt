package com.example.mealmanager.ui.theme.input

import androidx.lifecycle.ViewModel
import com.example.mealmanager.data.Meal
import com.example.mealmanager.data.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MealInputViewModel(private val repository: MealRepository) : ViewModel() {
    private val _mealState = MutableStateFlow(Meal())
    val mealState: StateFlow<Meal> = _mealState

    fun updateLocation(location: String) {
        _mealState.value = _mealState.value.copy(location = location)
    }

    fun updateReview(review: String) {
        _mealState.value = _mealState.value.copy(review = review)
    }

    fun updateMealType(mealType: String) {
        _mealState.value = _mealState.value.copy(mealType = mealType)
    }

    fun updateDate(date: String) {
        val formattedDate = formatDate(date)
        _mealState.value = _mealState.value.copy(date = formattedDate)
    }

    private fun formatDate(date: String): String {
        return try {
            val parsedDate = LocalDate.parse(date) // 입력된 날짜를 파싱
            parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // yyyy-MM-dd 형식으로 변환
        } catch (e: Exception) {
            println("Debug: 날짜 형식 변환 실패 - $date, ${e.message}")
            date // 변환 실패 시 원래 값 반환
        }
    }

    fun updateImageUri(uri: String) {
        _mealState.value = _mealState.value.copy(imageUri = uri)
    }

    fun updateFoodName(foodName: String) {
        _mealState.value = _mealState.value.copy(foodName = foodName)
    }

    fun updateSideDishes(sideDishes: String) {
        val sideDishesList = sideDishes.split(",").map { it.trim() } // 쉼표로 구분된 문자열을 리스트로 변환
        _mealState.value = _mealState.value.copy(sideDishes = sideDishesList)
    }


    fun updateCost(cost: Int) {
        _mealState.value = _mealState.value.copy(cost = cost)
    }

    fun saveMeal(meal: Meal) {
        val newMeal = meal.copy(
            id = generateMealId(),
            mealType = meal.mealType.trim(), // 공백 제거
            foodName = meal.foodName.trim().lowercase() // 소문자로 통일
        )
        println("Debug: Saving meal = $newMeal") // 저장되는 데이터 확인
        repository.addMeal(newMeal)
    }

    // ID 생성 함수
    private fun generateMealId(): Int {
        return (repository.getAllMeals().maxOfOrNull { it.id } ?: 0) + 1
    }
}