package com.educonnect.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object AdminHome : Screen("admin_home")
    object StudentHome : Screen("student_home")
    object TeacherHome : Screen("teacher_home")
    object AddPlanning : Screen("add_planing")
    object AddCampus : Screen("add_campus")
    object ListCampus : Screen("list_campus")
    object AddBuilding : Screen("add_building")
    object AddSalle : Screen("add_salle")
    object AddUser : Screen("add_user")
    object ListUsers : Screen("list_users")
    object MentionScreen: Screen("mention_screen")
    object ParcoursScreen: Screen("parcours_screen")
    object ListBuildings : Screen("list_buildings")
    object ListSalles : Screen("list_salles")
    object AddCoursScreen: Screen("add_cours_screen")
    object PlanningScreen : Screen("planning_screen")
}