package com.educonnect.ui.users

import CustomTopAppBar
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.educonnect.model.*
import com.educonnect.repository.UserRepository
import com.educonnect.repository.UserService
import com.educonnect.ui.components.*
import com.educonnect.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun ListUsersScreen(
    context: Context,
    navController: NavController,
    userRepository: UserRepository,
    userService: UserService,
    onLogout: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val studentList = remember { mutableStateOf<List<StudentDto>>(emptyList()) }
    val teacherList = remember { mutableStateOf<List<TeacherDto>>(emptyList()) }

    var editingUser by remember { mutableStateOf<UserRow?>(null) }
    var isEditingStudent by remember { mutableStateOf(true) }

    // Chargement initial des listes
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            studentList.value = userRepository.getAllStudents()
            teacherList.value = userRepository.getAllTeachers()
        }
    }

    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = { navController.navigate(Screen.AdminHome.route) },
                onSearch = { },
                onProfileClick = { },
                onLogoutClick = { onLogout() }
            )
        }
    ) {
        // En-tête + bouton d'ajout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Liste des utilisateurs",
                fontSize = 24.sp,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = { navController.navigate(Screen.AddUser.route) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ajouter",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp) // ✅ taille corrigée
                )
            }
        }

        // Liste des étudiants
        CustomUserListCard(
            title = "Liste des étudiants",
            users = studentList.value.map { it.toUserRow() },
            isStudent = true,
            onEdit = { user ->
                editingUser = user
                isEditingStudent = true
            },
            onDelete = { user ->
                coroutineScope.launch {
                    try {
                        userService.deleteStudent(user.id)
                        studentList.value = userRepository.getAllStudents()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Liste des enseignants
        CustomUserListCard(
            title = "Liste des enseignants",
            users = teacherList.value.map { it.toUserRow() },
            isStudent = false,
            onEdit = { user ->
                editingUser = user
                isEditingStudent = false
            },
            onDelete = { user ->
                coroutineScope.launch {
                    try {
                        userService.deleteTeacher(user.id)
                        teacherList.value = userRepository.getAllTeachers()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        // ✅ Dialogue de modification
        editingUser?.let { user ->
            EditUserDialog(
                user = user,
                isStudent = isEditingStudent,
                userService = userRepository, // important pour dropdowns
                onDismiss = { editingUser = null },
                onSubmit = { updatedRequest ->
                    coroutineScope.launch {
                        try {
                            if (isEditingStudent) {
                                userRepository.updateStudent(user.id, updatedRequest)
                                studentList.value = userRepository.getAllStudents()
                            } else {
                                userRepository.updateTeacher(user.id, updatedRequest)
                                teacherList.value = userRepository.getAllTeachers()
                            }
                            editingUser = null
                            Toast.makeText(context, "Utilisateur mis à jour avec succès", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    }
}
