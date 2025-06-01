package com.educonnect.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.educonnect.model.CourseDto
import androidx.compose.material3.CardDefaults



@Composable
fun CourseCard(
    course: CourseDto,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = listOf(
        Color(0xFFB0BEC5), Color(0xFFD1C4E9), Color(0xFF80DEEA),
        Color(0xFFFFF59D), Color(0xFFCE93D8), Color(0xFFB2EBF2),
        Color(0xFFFFCCBC), Color(0xFFC8E6C9), Color(0xFFCFD8DC)
    )
    val bgColor = remember { colors.random() }

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(bgColor)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = course.intitule,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )

            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text("VOIR", color = Color.White)
            }
        }
    }
}
