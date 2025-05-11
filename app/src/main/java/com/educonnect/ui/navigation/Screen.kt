package com.educonnect.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object AdminHome : Screen("admin_home")
    object StudentHome : Screen("student_home")
    object TeacherHome : Screen("teacher_home")
}