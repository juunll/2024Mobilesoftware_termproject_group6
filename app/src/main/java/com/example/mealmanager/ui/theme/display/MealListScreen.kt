package com.example.mealmanager.ui.theme.display

import android.app.DatePickerDialog
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mealmanager.data.DataSource
import com.example.mealmanager.data.Meal
import com.example.mealmanager.data.MealRepository
import java.util.Calendar
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MealListScreen(
    navController: NavController,
    repository: MealRepository
) {
    val viewModel: MealListViewModel = viewModel(factory = MealListViewModelFactory(repository))
    val meals by viewModel.meals.collectAsState()

    var selectedDate by remember { mutableStateOf("") }
    var totalCalories by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 날짜 선택 버튼
        DatePickerButton(onDateSelected = { date ->
            selectedDate = date
            viewModel.loadMealsByDate(date)
        })

        // 선택된 날짜 표시
        if (selectedDate.isNotEmpty()) {
            Text(
                text = "선택된 날짜: $selectedDate",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // 식사 목록 표시
        if (meals.isEmpty()) {
            Text("등록된 식사가 없습니다.")
        } else {
            totalCalories = 0 // 총 칼로리 초기화
            meals.forEach { meal ->
                val mealCalories = DataSource.calorieData[meal.foodName.lowercase()] ?: 0
                totalCalories += mealCalories

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("시간대: ${meal.mealType}")
                        Text("음식: ${meal.foodName}")
                        Text("칼로리: ${mealCalories} kcal")
                    }
                    Button(
                        onClick = {
                            navController.navigate("meal_detail/${meal.id}")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp)
                    ) {
                        Text("상세 보기", color = Color.White)
                    }
                }
            }

            // 총 칼로리 표시
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "총 칼로리: $totalCalories kcal",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun DatePickerButton(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(
        onClick = {
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$year-${month + 1}-$dayOfMonth"
                    onDateSelected(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(200.dp)
            .height(50.dp)
    ) {
        Text("날짜 선택", color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun MealListScreenPreview() {
    MealListScreen(
        navController = NavController(LocalContext.current),
        repository = MealRepository()
    )
}
