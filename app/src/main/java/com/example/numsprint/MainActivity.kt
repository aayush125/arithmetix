package com.example.numsprint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.numsprint.data.PreferenceDataStoreHelper
import com.example.numsprint.model.AppThemeNames
import com.example.numsprint.ui.theme.AppTheme
import com.example.numsprint.ui.theme.NumSprintTheme
import com.example.numsprint.utils.NumSprintScreens
import com.example.numsprint.viewmodel.ThemeViewModel
import com.example.numsprint.viewmodel.ThemeViewModelFactory
import kotlinx.coroutines.launch

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
            val theme = when(currentTheme) {
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
                            TestScreen()
                        }
                        composable(route = NumSprintScreens.StarterScreen.name) {
                            StarterScreen(navController)
                        }
                        composable(route = NumSprintScreens.Endless.name) {
                            Endless()
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