package com.educonnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.educonnect.di.Injection
import com.educonnect.di.Injection.provideParcoursService
import com.educonnect.di.NetworkModule
import com.educonnect.repository.UserRepository
import com.educonnect.ui.auth.ResponsiveLoginScreen
import com.educonnect.ui.building.AddBuildingScreen
import com.educonnect.ui.campus.AddCampusScreen
import com.educonnect.ui.campus.ListCampusScreen
import com.educonnect.ui.home.AdminHomeScreen
import com.educonnect.ui.home.StudentHomeScreen
import com.educonnect.ui.home.TeacherHomeScreen
import com.educonnect.ui.mentions.MentionScreen
import com.educonnect.ui.navigation.Screen.MentionScreen
import com.educonnect.ui.parcours.ParcoursScreen
import com.educonnect.ui.planning.AddPlanningScreen
import com.educonnect.ui.salle.AddSalleScreen
import com.educonnect.ui.users.AddUserScreen
import com.educonnect.ui.users.ListUsersScreen

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
                    "etudiant" -> navController.navigate(Screen.StudentHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                    "professeur" -> navController.navigate(Screen.TeacherHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
        }

        composable(Screen.AdminHome.route) {
            AdminHomeScreen(context = context, navController= navController, onLogout =  {
                performLogout(navController)
            })
        }

        composable(Screen.StudentHome.route) {
            StudentHomeScreen(context = context, onLogout =  {
                performLogout(navController)
            })
        }

        composable(Screen.TeacherHome.route) {
            TeacherHomeScreen(context = context, onLogout =  {
                performLogout(navController)
            })
        }

        composable(Screen.AddPlanning.route) { backStackEntry ->
            AddPlanningScreen(
                context = LocalContext.current,
                onLogout = {
                    performLogout(navController)
                },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.AddCampus.route) { backStackEntry ->
            AddCampusScreen(
                context = LocalContext.current,
                onLogout = {
                    performLogout(navController)
                },
                onNavigateToCampusList = {
                    navController.navigate(Screen.ListCampus.route)
                },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.ListCampus.route) {
            ListCampusScreen(
                context = context,
                navController = navController,
                onLogout = {
                    performLogout(navController)
                },
                onNavigateToAddCampus = {
                    navController.navigate(Screen.AddCampus.route)
                }
            )
        }


        composable(Screen.AddBuilding.route) { backStackEntry ->
            AddBuildingScreen(
                context = LocalContext.current,
                onLogout = {
                    performLogout(navController)
                },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.AddSalle.route) { backStackEntry ->
            AddSalleScreen(
                context = LocalContext.current,
                onLogout = {
                    performLogout(navController)
                },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.AddUser.route) {
            AddUserScreen(
                context = context, // ou LocalContext.current
                navController = navController,
                userService = Injection.provideUserService(),
                onLogout = {
                    performLogout(navController)
                },
            )
        }

        composable(Screen.ListUsers.route) {
            val context = LocalContext.current
            val userService = NetworkModule.userApi  // si tu utilises DI ou singleton
            val userRepository = UserRepository(userService)

            ListUsersScreen(
                context = context,
                navController = navController,
                userRepository = userRepository,
                userService = userService,
                onLogout = {
                    performLogout(navController)
                },
            )
        }

        composable(MentionScreen.route) {
            MentionScreen(
                context = context,
                navController = navController,
                mentionService = Injection.provideMentionService(),
                onLogout = { /* ton callback logout ici */ }
            )
        }
        // Ajoute de la route vers Parcours
        composable(Screen.ParcoursScreen.route) {
            ParcoursScreen(
                context = context,
                navController = navController,
                parcoursService = Injection.provideParcoursService(),
                mentionRepository = Injection.provideMentionRepository(), // <-- Attention à bien utiliser la bonne méthode
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }





    }
}

fun performLogout(navController: NavHostController) {
    navController.navigate(Screen.Login.route) {
        popUpTo(Screen.AdminHome.route) { inclusive = true }
    }
}

