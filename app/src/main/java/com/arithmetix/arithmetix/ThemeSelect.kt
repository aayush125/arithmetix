package com.arithmetix.arithmetix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arithmetix.arithmetix.model.AppThemeNames
import com.arithmetix.arithmetix.viewmodel.LocalPreferencesViewModel

@Composable
fun ThemeSelect(viewModel: LocalPreferencesViewModel) {
    val currentTheme by viewModel.themePreference.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                viewModel.updateTheme(AppThemeNames.DARK.name)
            }) {
                Text(
                    text = "Theme Dark"
                )
            }
            Button(onClick = {
                viewModel.updateTheme(AppThemeNames.BLUE.name)
            }) {
                Text(
                    text = "Theme Blue"
                )
            }
            Button(onClick = {
                viewModel.updateTheme(AppThemeNames.LIGHT.name)
            }) {
                Text(
                    text = "Theme Light"
                )
            }

            Text("Current theme: $currentTheme")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ThemeSelectPreview() {
//    ThemeSelect()
//}