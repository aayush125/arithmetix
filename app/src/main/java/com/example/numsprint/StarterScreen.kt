package com.example.numsprint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.numsprint.ui.theme.NumSprintTheme
import com.example.numsprint.utils.NumSprintScreens

@Composable
fun StarterScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                // removing the innerPadding here truly makes the edge-to-edge look
                // using it restricts the viewport between the system bars
//                    .padding(innerPadding)
                .fillMaxSize(),
//                    .background(Color.Cyan),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(onClick = { navController.navigate(NumSprintScreens.Endless.name) }) {
                Text(text = "Open game")
            }
            Button(onClick = { navController.navigate(NumSprintScreens.ThemeSelect.name) }) {
                Text(text = "Select theme")
            }
            Button(onClick = { navController.navigate(NumSprintScreens.TestScreen.name) }) {
                Text(text = "Test Screen")
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun StarterScreenPreview() {
//    StarterScreen()
//}