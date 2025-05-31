package com.educonnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.educonnect.di.AppSession
import com.educonnect.ui.navigation.AppNavigation
import com.educonnect.ui.navigation.Screen
import com.educonnect.ui.theme.Educonnect_FrontTheme
import com.educonnect.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialiser le singleton
        AppSession.sessionManager = SessionManager(this);
        enableEdgeToEdge()

        setContent {
            Educonnect_FrontTheme {
                val navController = rememberNavController()
//                val sessionManager = remember { SessionManager(this) }

                // State to track session status
                var isSessionChecked by remember { mutableStateOf(false) }
                var startDestination by remember { mutableStateOf(Screen.Login.route) }

                // Vérification de la session avant le rendu
                LaunchedEffect(Unit) {
                    val userData = withContext(Dispatchers.IO) {
                        AppSession.sessionManager.getUserData()
                    }

                    startDestination = when (userData?.role?.lowercase()) {
                        "admin" -> Screen.AdminHome.route
                        "student" -> Screen.StudentHome.route
                        "teacher" -> Screen.TeacherHome.route
                        else -> Screen.Login.route
                    }

                    isSessionChecked = true
                }

                if (!isSessionChecked) {
                    // Afficher le chargement pendant la vérification de la session
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Gray, strokeWidth = 3.dp)
                    }
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        AppNavigation(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}
