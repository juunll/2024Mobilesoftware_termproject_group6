package com.example.mealmanager.ui.theme.display

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mealmanager.data.Meal
import androidx.compose.ui.Modifier // 올바른 Modifier import
import androidx.compose.foundation.layout.padding // padding 관련 함수 import
import androidx.compose.foundation.layout.fillMaxWidth // fillMaxWidth 관련 함수 import
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.mealmanager.data.MealRepository


@Composable
fun MealDetailScreen(
    meal: Meal?, // Meal 객체를 nullable로 변경
    onBack: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (meal != null) {
            Text("날짜: ${meal.date}", style = MaterialTheme.typography.headlineSmall)
            Text("장소: ${meal.location}")
            Text("음식: ${meal.foodName}")
            Text("반찬: ${meal.sideDishes.joinToString(", ")}")
            Text("리뷰: ${meal.review}")
            Text("비용: ${meal.cost}원")

            meal.imageUri?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "음식 이미지",
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                )
            }
        } else {
            Text("식사 정보를 찾을 수 없습니다.", style = MaterialTheme.typography.bodyLarge)
        }

        Button(onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            modifier = Modifier.padding(top = 16.dp)
                ,shape = RoundedCornerShape(16.dp),) {
            Text("뒤로가기")
        }
    }
}


