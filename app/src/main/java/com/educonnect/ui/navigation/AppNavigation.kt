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

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object AdminHome : Screen("admin_home")
    object StudentHome : Screen("student_home")
    object TeacherHome : Screen("teacher_home")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier= Modifier
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            Log.d("AppNavigation", "Composing ResponsiveLoginScreen")
            ResponsiveLoginScreen { role ->
                Log.d("AppNavigation", "Role received in AppNavigation: $role")
                when (role) {
                    "admin" -> {
                        Log.d("AppNavigation", "Navigating to AdminHome")
                        navController.navigate(Screen.AdminHome.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                    "student" -> {
                        navController.navigate(Screen.StudentHome.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                    "teacher" -> {
                        navController.navigate(Screen.TeacherHome.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
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
