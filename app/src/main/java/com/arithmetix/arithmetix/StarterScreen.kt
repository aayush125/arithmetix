package com.arithmetix.arithmetix

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arithmetix.arithmetix.ui.components.MenuBG
import com.arithmetix.arithmetix.ui.components.UIButton
import com.arithmetix.arithmetix.utils.NumSprintScreens

@Composable
fun StarterScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MenuBG()
            Column(
                modifier = Modifier
                    // removing the innerPadding here truly makes the edge-to-edge look
                    // using it restricts the viewport between the system bars
//                    .padding(innerPadding)
                    .fillMaxSize().background(Color.Transparent),
//                    .background(Color.Cyan),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
//            Button(onClick = { navController.navigate(NumSprintScreens.TimeAttack.name) }) {
//                Text(text = "Time Attack")
//            }
                UIButton(
                    onClick = { navController.navigate(NumSprintScreens.TimeAttack.name) },
                    buttonText = "Time Attack",
                    modifier = Modifier.width(250.dp)
                )
//            Button(onClick = { navController.navigate(NumSprintScreens.Endless.name) }) {
//                Text(text = "Endless")
//            }
                UIButton(
                    onClick = { navController.navigate(NumSprintScreens.Endless.name) },
                    buttonText = "Endless",
                    modifier = Modifier.width(250.dp)
                )
//            Button(onClick = { navController.navigate(NumSprintScreens.ThemeSelect.name) }) {
//                Text(text = "Select theme")
//            }
                UIButton(
                    onClick = { navController.navigate(NumSprintScreens.ThemeSelect.name) },
                    buttonText = "Select Theme",
                    modifier = Modifier.width(250.dp)
                )
//            Button(onClick = { navController.navigate(NumSprintScreens.TestScreen.name) }) {
//                Text(text = "Test Screen")
//            }
                UIButton(
                    onClick = { navController.navigate(NumSprintScreens.TestScreen.name) },
                    buttonText = "Test Screen",
                    modifier = Modifier.width(250.dp)
                )
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun StarterScreenPreview() {
//    StarterScreen()
//}