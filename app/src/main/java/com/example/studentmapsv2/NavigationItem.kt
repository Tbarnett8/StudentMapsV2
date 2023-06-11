package com.example.studentmapsv2

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val label: String, val icons: ImageVector) {

    object Homepage : NavigationItem("homepage", "Homepage", Icons.Default.Home)
    object Map : NavigationItem("map", "Map", Icons.Default.LocationOn)
    object Settings : NavigationItem("settings", "Settings", Icons.Default.Settings)

}
