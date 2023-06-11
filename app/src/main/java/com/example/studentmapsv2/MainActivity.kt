package com.example.studentmapsv2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studentmapsv2.homepage.HomepageScreen
import com.example.studentmapsv2.map.MapScreen
import com.example.studentmapsv2.settings.SettingsScreen
import com.example.studentmapsv2.ui.theme.AccentColor
import com.example.studentmapsv2.ui.theme.HighlightColor
import com.example.studentmapsv2.ui.theme.StudentMapsV2Theme
import com.example.studentmapsv2.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentMapsV2Theme {
                Navigation()
            }
        }
    }
}

@Composable
fun NavigationController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.Homepage.route) {

        composable(NavigationItem.Homepage.route) {
            HomepageScreen()
        }

        composable(NavigationItem.Map.route) {
            MapScreen()
        }

        composable(NavigationItem.Settings.route) {
            SettingsScreen()
        }

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {

    val navController = rememberNavController()

    val items = listOf(
        NavigationItem.Homepage,
        NavigationItem.Map,
        NavigationItem.Settings,
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = AccentColor) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { it ->
                    NavigationBarItem(selected = currentRoute == it.route,
                        label = {
                            Text(
                                text = it.label,
                                color = if (currentRoute == it.route) HighlightColor else White,
                                style = TextStyle.Default.copy(fontWeight = FontWeight.Bold)
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = it.icons, contentDescription = null,
                                tint = if (currentRoute == it.route) HighlightColor else White
                            )
                        },
                        onClick = {
                            if (currentRoute != it.route) {
                                navController.graph.startDestinationRoute?.let { it2 ->
                                    navController.popBackStack(it2, true)
                                }
                                navController.navigate(it.route) {
                                    launchSingleTop = true
                                }
                            }
                        })
                }
            }
        }) {
        NavigationController(navController = navController)
    }

}