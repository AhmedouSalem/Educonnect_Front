package com.educonnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.educonnect.ui.auth.ResponsiveLoginScreen
import com.educonnect.ui.building.AddBuildingScreen
import com.educonnect.ui.campus.AddCampusScreen
import com.educonnect.ui.home.AdminHomeScreen
import com.educonnect.ui.home.StudentHomeScreen
import com.educonnect.ui.home.TeacherHomeScreen
import com.educonnect.ui.planning.AddPlanningScreen
import com.educonnect.ui.salle.AddSalleScreen

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
            AdminHomeScreen(context = context, navController= navController) {
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

        composable(Screen.AddPlanning.route) { backStackEntry ->
            AddPlanningScreen(
                context = LocalContext.current,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.AddCampus.route) { backStackEntry ->
            AddCampusScreen(
                context = LocalContext.current,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.AddBuilding.route) { backStackEntry ->
            AddBuildingScreen(
                context = LocalContext.current,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.AddSalle.route) { backStackEntry ->
            AddSalleScreen(
                context = LocalContext.current,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}
