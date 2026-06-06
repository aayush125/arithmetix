package com.arithmetix.arithmetix

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.ui.components.AppInfo
import com.arithmetix.arithmetix.ui.components.MenuBG
import com.arithmetix.arithmetix.ui.components.MenuOptionModal
import com.arithmetix.arithmetix.ui.components.Settings
import com.arithmetix.arithmetix.utils.NumSprintScreens
import com.arithmetix.arithmetix.viewmodel.LocalPreferencesViewModel

enum class MenuModals(val title: String) {
    SETTINGS(title = "Settings"),
    LEADERBOARD(title = "Leaderboard"),
    STATS(title = "Statistics"),
    APP_INFO(title = "Arithmetix"),
    NONE(title = "")
}

@Composable
fun GameModeMenuCard(
    iconId: Int,
    title: String,
    onClick: () -> Unit,
    highScore: Int = 0
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,  // Controls the bounciness
            stiffness = Spring.StiffnessMedium              // Controls the speed of animation
        ),
        label = "Card scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .scale(scale),
//            .border(4.dp, Color.Red, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = "Play Mode Icon",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 5.dp)
            )
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
            if (highScore > 0) {
                Text(
                    text = "High score: $highScore",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun IconButton(
    iconId: Int,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val btnScaleAnimation by animateFloatAsState(
        targetValue = if (isPressed) 0.78f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,  // Controls the bounciness
            stiffness = Spring.StiffnessMedium              // Controls the speed of animation
        ),
        label = "IconButtonSize"
    )

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = "IconButton Icon",
            modifier = Modifier
                .size(30.dp)
                .scale(btnScaleAnimation)
        )
    }
}

@Composable
fun Menu(preferencesViewModel: LocalPreferencesViewModel, onNavigateToGame: (String) -> Unit) {
    var showModal by remember { mutableStateOf(false) }
    var modalToShow by remember { mutableStateOf(MenuModals.NONE) }

    BackHandler(enabled = showModal) {
        showModal = false
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            MenuBG()
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .height(400.dp),
//                .border(2.dp, Color.Green),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(0.dp)
//                    .border(2.dp, Color.Red)
                        .weight(2f),
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        GameModeMenuCard(
                            iconId = R.drawable.endless_icon,
                            title = "Endless",
                            onClick = { onNavigateToGame(NumSprintScreens.Endless.name) },
                            highScore = 20
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        GameModeMenuCard(
                            iconId = R.drawable.timed_icon,
                            title = "Time Attack",
                            onClick = { onNavigateToGame(NumSprintScreens.TimeAttack.name) },
                            highScore = 24
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
//                        .border(2.dp, Color.Blue),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.4f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            iconId = R.drawable.baseline_leaderboard_24,
                            onClick = {
                                modalToShow = MenuModals.LEADERBOARD
                                showModal = true
                            }
                        )
                        IconButton(
                            iconId = R.drawable.baseline_align_horizontal_left_24,
                            onClick = {
                                modalToShow = MenuModals.STATS
                                showModal = true
                            }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(0.4f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            iconId = R.drawable.baseline_settings_24,
                            onClick = {
                                modalToShow = MenuModals.SETTINGS
                                showModal = true
                            }
                        )
                        IconButton(
                            iconId = R.drawable.baseline_info_24,
                            onClick = {
                                modalToShow = MenuModals.APP_INFO
                                showModal = true
                            }
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = showModal,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                val currentTheme by preferencesViewModel.themePreference.collectAsState()
                val currentKeypad by preferencesViewModel.keypadPreference.collectAsState()

                MenuOptionModal(title = modalToShow.title, onClose = {
                    showModal = false
                }) {
                    when (modalToShow) {
                        MenuModals.SETTINGS -> Settings(
                            currentTheme = currentTheme,
                            onThemeChange = { theme -> preferencesViewModel.updateTheme(theme) },
                            currentKeypad = currentKeypad,
                            onKeypadChange = { keypad -> preferencesViewModel.updateKeypad(keypad) }
                        )

                        MenuModals.APP_INFO -> AppInfo()
                        MenuModals.STATS -> {}
                        MenuModals.LEADERBOARD -> {}
                        MenuModals.NONE -> {}
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
//    Menu()
//    IconButton()
}