package com.example.mealmanager.ui.theme.analysis

import android.app.DatePickerDialog
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmanager.data.MealRepository
import java.util.Calendar


@Composable
fun MealAnalysisScreen(repository: MealRepository) {
    val viewModel: MealAnalysisViewModel = viewModel(factory = MealAnalysisViewModelFactory(repository))
    val analysisData by viewModel.analysisData.collectAsState()
    var selectedMonth by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 제목
        Text(
            text = "월별 식사 분석",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 날짜 선택 버튼
        DatePickerButton(onDateSelected = { month ->
            selectedMonth = month
            viewModel.loadMealsForMonth(month)
        })

        // 선택된 달 표시
        if (selectedMonth.isNotEmpty()) {
            Text(
                text = "선택된 달: $selectedMonth",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 분석 결과 표시
        if (analysisData.totalCalories > 0) {
            Text("조식 비용: ${analysisData.costByMealType["조식"] ?: 0} 원",
                style = MaterialTheme.typography.bodyLarge)
            Text("중식 비용: ${analysisData.costByMealType["중식"] ?: 0} 원",
                style = MaterialTheme.typography.bodyLarge)
            Text("석식 비용: ${analysisData.costByMealType["석식"] ?: 0} 원",
                style = MaterialTheme.typography.bodyLarge)
            Text("간식/음료 비용: ${analysisData.costByMealType["간식/음료"] ?: 0} 원",
                style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "총 칼로리: ${analysisData.totalCalories} kcal",
                style = MaterialTheme.typography.headlineSmall.copy( // 기본 스타일 복사 후 수정
                    fontSize = 24.sp, // 글씨 크기를 24sp로 설정
                    fontWeight = FontWeight.Bold // 글씨를 두껍게 설정
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            Text("해당 달에 대한 데이터가 없습니다!", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun DatePickerButton(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(onClick = {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, _ ->
                val selectedMonth = "$year-${month + 1}" // YYYY-MM 형식
                onDateSelected(selectedMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
            ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ,shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(200.dp)
            .height(50.dp)) {
        Text("분석할 달 선택")
    }
}
