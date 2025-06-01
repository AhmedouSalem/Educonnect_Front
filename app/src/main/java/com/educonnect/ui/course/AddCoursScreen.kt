package com.educonnect.ui.course

import CustomTopAppBar
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.educonnect.di.Injection.provideTeacherHomeViewModel
import com.educonnect.model.CourseDto
import com.educonnect.repository.CourseService
import com.educonnect.repository.ParcoursService
import com.educonnect.repository.UserRepository
import com.educonnect.ui.components.CourseDialog
import com.educonnect.ui.components.ScrollableFormLayout
import com.educonnect.ui.home.TeacherHomeViewModel
import com.educonnect.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun AddCoursScreen(
    context: Context,
    navController: NavController,
    courseService: CourseService,
    parcoursService: ParcoursService,
    userRepository: UserRepository,
    onLogout: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val courseList = remember { mutableStateListOf<CourseDto>() }
    var showDialog by remember { mutableStateOf(false) }
    var editingCourse by remember { mutableStateOf<CourseDto?>(null) }

    val viewModel: TeacherHomeViewModel = remember { provideTeacherHomeViewModel(context) }
    val teacherData by viewModel.teacherData.collectAsState()
    val connectedUserId = remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(teacherData) {
        teacherData?.id?.let {
            connectedUserId.value = it
            scope.launch {
                try {
                    val response = courseService.getCourses()
                    if (response.isSuccessful) {
                        courseList.clear()
                        response.body()?.let { courseList.addAll(it) }
                    } else {
                        Toast.makeText(context, "Erreur chargement : ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("AddCoursScreen", "Erreur chargement", e)
                    Toast.makeText(context, "Erreur de chargement", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (connectedUserId.value == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = { navController.popBackStack() },
                onSearch = {},
                onProfileClick = {},
                onLogoutClick = { onLogout() }
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Gestion des cours",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                color = Primary,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                editingCourse = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = Color.Black)
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Intitulé", modifier = Modifier.weight(0.2f), color = Primary)
            Text("Description", modifier = Modifier.weight(0.25f), color = Primary)
            Text("Parcours", modifier = Modifier.weight(0.25f), color = Primary)
            Text("Actions", modifier = Modifier.weight(0.3f), color = Primary)
        }

        courseList.forEach { cours ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(cours.intitule, modifier = Modifier.weight(0.2f))
                Text(cours.description, modifier = Modifier.weight(0.25f))
                Text(cours.parcours.joinToString(), modifier = Modifier.weight(0.25f))

                Row(modifier = Modifier.weight(0.3f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = {
                        editingCourse = cours
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Modifier", tint = Color.Black)
                    }

                    IconButton(onClick = {
                        scope.launch {
                            try {
                                val result = courseService.deleteCourse(cours.id!!)
                                if (result.isSuccessful) {
                                    courseList.remove(cours)
                                    Toast.makeText(context, "Cours supprimé", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Erreur suppression : ${result.code()}", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Log.e("AddCoursScreen", "Erreur suppression", e)
                                Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Red)
                    }
                }
            }
        }

        if (showDialog) {
            CourseDialog(
                context = context,
                initialCourse = editingCourse,
                onDismiss = { showDialog = false },
                onSubmit = { dto ->
                    scope.launch {
                        try {
                            val completedDto = dto.copy(teacherId = connectedUserId.value)
                            val result = if (editingCourse == null) {
                                courseService.addCourse(completedDto)
                            } else {
                                courseService.updateCourse(editingCourse!!.id!!, completedDto)
                            }

                            if (result.isSuccessful) {
                                val refresh = courseService.getCourses()
                                if (refresh.isSuccessful) {
                                    courseList.clear()
                                    refresh.body()?.let { courseList.addAll(it) }
                                }
                                showDialog = false
                                Toast.makeText(context, "Cours sauvegardé", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Erreur serveur : ${result.code()}", Toast.LENGTH_SHORT).show()
                            }

                        } catch (e: Exception) {
                            Log.e("AddCoursScreen", "Erreur sauvegarde", e)
                            Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                parcoursService = parcoursService,
                userRepository = userRepository,
                connectedTeacherId = connectedUserId.value!!
            )
        }
    }
}
