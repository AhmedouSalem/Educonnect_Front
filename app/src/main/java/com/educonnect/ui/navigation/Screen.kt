package com.educonnect.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object AdminHome : Screen("admin_home")
    object StudentHome : Screen("student_home")
    object TeacherHome : Screen("teacher_home")
    object AddPlanning : Screen("add_planing")
    object AddCampus : Screen("add_campus")
    object AddBuilding : Screen("add_building")
    object AddSalle : Screen("add_salle")
    object AddUser : Screen("add_user")

}