package com.arithmetix.arithmetix

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
import com.arithmetix.arithmetix.model.KeypadStyles
import com.arithmetix.arithmetix.ui.theme.AppTheme
import com.arithmetix.arithmetix.ui.theme.NumSprintTheme
import com.arithmetix.arithmetix.utils.NumSprintScreens
import com.arithmetix.arithmetix.viewmodel.LocalPreferencesViewModel
import com.arithmetix.arithmetix.viewmodel.LocalPreferencesViewModelFactory

class MainActivity : ComponentActivity() {
    private val localPreferencesViewModel: LocalPreferencesViewModel by viewModels {
        LocalPreferencesViewModelFactory(
            PreferenceDataStoreHelper(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val currentTheme by localPreferencesViewModel.themePreference.collectAsState()
            val currentKeypad by localPreferencesViewModel.keypadPreference.collectAsState()

            val theme = when (currentTheme) {
                AppThemeNames.LIGHT.name -> AppTheme.light
                AppThemeNames.DARK.name -> AppTheme.dark
                AppThemeNames.BLUE.name -> AppTheme.blue
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
//                            WitheringRedBackground()
                            Menu(
                                preferencesViewModel = localPreferencesViewModel,
                                onNavigateToGame = { gameMode -> navController.navigate(gameMode) }
                            )
                        }
                        composable(route = NumSprintScreens.StarterScreen.name) {
//                            StarterScreen(navController)
                            Menu(
                                preferencesViewModel = localPreferencesViewModel,
                                onNavigateToGame = { gameMode -> navController.navigate(gameMode) }
                            )
                        }
                        composable(route = NumSprintScreens.Endless.name) {
                            Endless(
                                onBackNavigation = {
                                    navController.navigate(NumSprintScreens.StarterScreen.name) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                keypadReversed = currentKeypad == KeypadStyles.PHONE.name
                            )
                        }
                        composable(route = NumSprintScreens.TimeAttack.name) {
                            TimeAttack(
                                navController,
                                keypadReversed = currentKeypad == KeypadStyles.PHONE.name
                            )
                        }
                        composable(route = NumSprintScreens.ThemeSelect.name) {
                            ThemeSelect(viewModel = localPreferencesViewModel)
                        }
                    }
                }
            }
        }
    }
}