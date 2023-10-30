package com.JetpackPractice.twitterui.routing

sealed class Screen(val title: String, val route: String) {
    object Home : Screen("Home", "Home")
    object Search : Screen("Search", "Search")
    object Notifications : Screen("Notifications", "Notifications")
    object Inbox : Screen("Inbox", "Inbox")
    object Profile : Screen ("Profile", "Profile")
}