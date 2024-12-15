package com.example.mealmanager.data

data class Meal(
    val id: Int=0,                 // 고유 ID
    val location: String="",        // 식사 장소
    val foodName: String="",        // 음식 이름
    val sideDishes: List<String> = emptyList(),// 반찬 목록
    val review: String="",          // 리뷰
    val mealType: String="",        // 식사 종류 (조식, 중식 등)
    val date: String="",            // 날짜
    val cost: Int =0,               // 비용
    val imageUri: String? = null,
    val calories: Int = 0// 이미지 URI
)

