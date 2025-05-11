package com.educonnect.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.educonnect.ui.auth.ResponsiveLoginScreen
import com.educonnect.ui.home.AdminHomeScreen
import com.educonnect.ui.home.StudentHomeScreen
import com.educonnect.ui.home.TeacherHomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            ResponsiveLoginScreen { role ->
                when (role) {
                    "admin" -> navController.navigate(Screen.AdminHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                    "student" -> navController.navigate(Screen.StudentHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                    "teacher" -> navController.navigate(Screen.TeacherHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
        }

        composable(Screen.AdminHome.route) {
            AdminHomeScreen(context = context) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.AdminHome.route) { inclusive = true }
                }
            }
        }

        composable(Screen.StudentHome.route) {
            StudentHomeScreen(context = context) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.StudentHome.route) { inclusive = true }
                }
            }
        }

        composable(Screen.TeacherHome.route) {
            TeacherHomeScreen(context = context) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.TeacherHome.route) { inclusive = true }
                }
            }
        }
    }
}
