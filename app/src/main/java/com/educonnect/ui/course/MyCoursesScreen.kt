package com.educonnect.ui.course

import CustomTopAppBar
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.educonnect.R
import com.educonnect.model.CourseDto
import com.educonnect.repository.CourseRepository
import com.educonnect.repository.UserRepository
import com.educonnect.ui.components.CourseCard
import com.educonnect.ui.components.ScrollableFormLayout
import kotlinx.coroutines.launch


import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCoursesScreen(
    navController: NavController,
    courseRepository: CourseRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val courseList = remember { mutableStateListOf<CourseDto>() }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val result = courseRepository.getCourses()
                courseList.clear()
                courseList.addAll(result)
            } catch (e: Exception) {
                Toast.makeText(context, "Erreur de chargement : ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(

    ) {
        Image(
            painter = painterResource(id = R.drawable.app_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Column {
            CustomTopAppBar(
                onHomeClick = { navController.popBackStack() },
                onSearch = { /* Rechercher un cours */ },
                onProfileClick = { /* Accès profil */ },
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "MES COURS",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(courseList) { course ->
                    CourseCard(course = course) {
                        Toast.makeText(context, "Voir ${course.intitule}", Toast.LENGTH_SHORT).show()
                        navController.navigate("course_detail/${course.id}/${course.intitule}")
                    }

                }
            }
        }
    }
}
