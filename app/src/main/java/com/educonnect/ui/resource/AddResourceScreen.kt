package com.educonnect.ui.resource

import CustomTopAppBar
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.educonnect.R
import com.educonnect.model.ResourceDto
import com.educonnect.model.enums.ResourceType
import com.educonnect.repository.ResourceRepository
import com.educonnect.ui.components.CustomAdminAddButton
import com.educonnect.ui.components.CustomAdminAddPageTitleTextView
import com.educonnect.ui.components.CustomAdminFormTextField
import com.educonnect.ui.components.DropdownMenuType
import com.educonnect.ui.components.ScrollableFormLayout
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResourceScreen(
    courseId: Long,
    courseTitle: String,
    navController: NavController,
    resourceRepository: ResourceRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var nom by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(ResourceType.PDF.name) }
    var categorie by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedFileUri = uri
    }

    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = { navController.popBackStack() },
                onSearch = { },
                onProfileClick = { },
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    ) {
        CustomAdminAddPageTitleTextView(text = "Ajout d’une ressource")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .width(500.dp)
                    .padding(16.dp)
            ) {
                CustomAdminFormTextField(value = nom, label = "Nom") { nom = it }

                DropdownMenuType(
                    selected = type,
                    onSelected = { type = it }
                )

                CustomAdminFormTextField(value = categorie, label = "Catégorie") { categorie = it }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { filePickerLauncher.launch("*/*") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF424242))
                ) {
                    Text(
                        text = selectedFileUri?.lastPathSegment ?: "Choisir un fichier",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomAdminAddButton(
                    buttonText = "Enregistrer",
                    onAddClick = {
                        if (nom.isBlank() || categorie.isBlank() || selectedFileUri == null) {
                            Toast.makeText(context, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show()
                            return@CustomAdminAddButton
                        }

                        val resource = ResourceDto(
                            nom = nom,
                            type = type,
                            categorie = categorie,
                            url = selectedFileUri.toString(),
                            taille = 0L,
                            courseId = courseId
                        )

                        scope.launch {
                            try {
                                resourceRepository.createResource(resource)
                                Toast.makeText(context, "Ressource ajoutée", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Erreur d'ajout", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
        }
    }
}
