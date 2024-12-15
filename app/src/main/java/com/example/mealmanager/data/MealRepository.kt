package com.example.mealmanager.data

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import android.util.Log
import java.time.format.DateTimeFormatter

class MealRepository {
    private val meals = mutableListOf<Meal>(
    )

    // 식사 추가
    fun addMeal(meal: Meal) {
        println("Debug: Adding meal = $meal")
        meals.add(meal)
    }

    fun getMealsByMonth(month: String): List<Meal> {
        return meals.filter { it.date.startsWith(month) }
    }


    // 최근 1달 간의 식사 가져오기
    fun getMealsForLastMonth(): List<Meal> {
        val currentDate = LocalDate.now()
        return meals.filter { meal ->
            try {
                val mealDate = LocalDate.parse(meal.date)
                ChronoUnit.DAYS.between(mealDate, currentDate) <= 30
            } catch (e: Exception) {
                false // 날짜 형식이 잘못된 경우 제외
            }
        }
    }

    fun getMealsByDate(date: String): List<Meal> {
        val formattedDate = formatDate(date)
        return meals.filter { it.date == formattedDate }
    }

    // 날짜 형식 변환 함수
    private fun formatDate(date: String): String {
        return try {
            val parsedDate = LocalDate.parse(date) // 입력된 날짜를 파싱
            parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // yyyy-MM-dd 형식으로 변환
        } catch (e: Exception) {
            println("Debug: 날짜 형식 변환 실패 - $date, ${e.message}")
            date // 변환 실패 시 원래 값 반환
        }
    }


    fun getMealById(mealId: Int): Meal? {
        return meals.find { it.id == mealId } // meals는 기존의 Meal 리스트
    }

    fun getCalorieForMeal(foodName: String): Int {
        return DataSource.calorieData[foodName.lowercase()] ?: 0
    }


    // 모든 식사 데이터 가져오기
    fun getAllMeals(): List<Meal> {
        return meals
    }

    // 테스트 데이터를 추가하는 메서드
    fun addTestData() {
        val testMeals = listOf<Meal>(
        )
        meals.addAll(testMeals)
    }
}
