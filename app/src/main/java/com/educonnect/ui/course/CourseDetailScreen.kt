package com.educonnect.ui.course

import CustomTopAppBar
import com.educonnect.ui.components.ResourceRow
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.educonnect.R
import com.educonnect.model.ResourceDto
import com.educonnect.repository.ResourceRepository
import kotlinx.coroutines.launch

@Composable
fun CourseDetailScreen(
    courseId: Long,
    courseTitle: String,
    navController: NavController,
    resourceRepository: ResourceRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val allResources = remember { mutableStateListOf<ResourceDto>() }

    val groupedResources by remember {
        derivedStateOf { allResources.groupBy { it.categorie } }
    }

    LaunchedEffect(courseId) {
        scope.launch {
            try {
                val data = resourceRepository.getResourcesByCourseId(courseId)
                allResources.clear()
                allResources.addAll(data)
            } catch (e: Exception) {
                Toast.makeText(context, "Erreur chargement ressources", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.app_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CustomTopAppBar(
                onHomeClick = { navController.popBackStack() },
                onSearch = {},
                onProfileClick = {},
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = courseTitle,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    navController.navigate("add_resource/$courseId/$courseTitle")
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = Color.Yellow)
                }
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                groupedResources.forEach { (categorie, list) ->
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0x80212121))
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(
                                    text = categorie,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Yellow
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                list.forEach { resource ->
                                    ResourceRow(resource = resource, onDelete = {
                                        scope.launch {
                                            try {
                                                resourceRepository.deleteResource(resource.id)
                                                allResources.remove(resource)
                                                navController.navigate("course_detail/${courseId}/${courseTitle}")
                                                Toast.makeText(context, "Supprimé", Toast.LENGTH_SHORT).show()
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "Erreur suppression", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Rediriger vers forum */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text("Accéder au forum", color = Color.White)
            }
        }
    }
}
