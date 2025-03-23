package moe.smoothie.androidide.themestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.Dispatchers.Main

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidIDEThemesTheme {
                MainActivityView()
            }
        }
    }
}

@Composable
fun MainActivityView() {
    val routes = listOf(
        NavigationBarRoute(
            nameResource = R.string.source_jetbrains,
            iconResource = R.drawable.icons8_jetbrains,
            route = "jetbrains-marketplace"
        ),
        NavigationBarRoute(
            nameResource = R.string.source_vscode,
            iconResource = R.drawable.icons8_visual_studio,
            route = "vscode-marketplace"
        ),
        NavigationBarRoute(
            nameResource = R.string.destination_settings,
            iconResource = R.drawable.baseline_settings_24,
            route = "settings"
        )
    )

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = routes.first().route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(routes[0].route) { JetbrainsStoreScroller() } // Ensure it's defined.
                composable(routes[1].route) { MicrosoftStoreScroller() } // Ensure it's defined.
                composable(routes[2].route) { PageContent("Settings will be here") }
            }
        },
        bottomBar = { BottomNavigationBar(navController, routes) }
    )
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    AndroidIDEThemesTheme {
        MainActivityView()
    }
}

@Composable
fun PageContent(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, routes: List<NavigationBarRoute>) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        routes.forEach { route ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(route.iconResource),
                        contentDescription = stringResource(route.nameResource),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(route.nameResource)) },
                selected = currentDestination?.route == route.route,
                onClick = {
                    if (currentDestination?.route != route.route) {
                        navController.navigate(route.route) {
                            launchSingleTop = true 
                            restoreState = true  
                        }
                    }
                }
            )
        }
    }
}