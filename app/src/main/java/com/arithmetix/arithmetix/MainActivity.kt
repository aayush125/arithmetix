package com.arithmetix.arithmetix

import WitheringRedBackground
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arithmetix.arithmetix.data.PreferenceDataStoreHelper
import com.arithmetix.arithmetix.model.AppThemeNames
import com.arithmetix.arithmetix.ui.theme.AppTheme
import com.arithmetix.arithmetix.ui.theme.NumSprintTheme
import com.arithmetix.arithmetix.utils.NumSprintScreens
import com.arithmetix.arithmetix.viewmodel.ThemeViewModel
import com.arithmetix.arithmetix.viewmodel.ThemeViewModelFactory

class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(
            PreferenceDataStoreHelper(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val currentTheme by themeViewModel.themePreference.collectAsState()
            val theme = when (currentTheme) {
                AppThemeNames.light.name -> AppTheme.light
                AppThemeNames.dark.name -> AppTheme.dark
                AppThemeNames.blue.name -> AppTheme.blue
                else -> AppTheme.light
            }
            NumSprintTheme(customTheme = theme) {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NumSprintScreens.StarterScreen.name
                    ) {
                        composable(route = NumSprintScreens.TestScreen.name) {
                            WitheringRedBackground()
                        }
                        composable(route = NumSprintScreens.StarterScreen.name) {
                            StarterScreen(navController)
                        }
                        composable(route = NumSprintScreens.Endless.name) {
                            Endless(onBackNavigation = {
                                navController.navigate(NumSprintScreens.StarterScreen.name) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            })
                        }
                        composable(route = NumSprintScreens.TimeAttack.name) {
                            TimeAttack(navController)
                        }
                        composable(route = NumSprintScreens.ThemeSelect.name) {
                            ThemeSelect(viewModel = themeViewModel)
                        }
                    }
                }
            }
        }
    }
}