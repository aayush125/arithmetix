package com.arithmetix.arithmetix

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arithmetix.arithmetix.model.GameModes
import com.arithmetix.arithmetix.ui.components.MenuBG
import com.arithmetix.arithmetix.ui.components.PostGameOverlay
import com.arithmetix.arithmetix.ui.components.ProblemKeypad
import com.arithmetix.arithmetix.ui.components.ScoreComponent
import com.arithmetix.arithmetix.ui.components.Timer
import com.arithmetix.arithmetix.utils.NumSprintScreens
import com.arithmetix.arithmetix.utils.timeAttackTime
import com.arithmetix.arithmetix.viewmodel.GameSessionViewModel
import com.arithmetix.arithmetix.viewmodel.GameSessionViewModelFactory
import kotlinx.coroutines.delay

@Composable
fun TimeAttack(navController: NavController, overlayVisiblePreview: Boolean = false) {
    val viewModel: GameSessionViewModel = viewModel(
        factory = GameSessionViewModelFactory(GameModes.TIME_ATTACK)
    )

    var overlayVisible by remember { mutableStateOf(true) } // make this true for final product
    var gameOver by remember { mutableStateOf(false) } // make this false for final product
    var showPostGameOverlay by remember { mutableStateOf(false) }

    val blurValue by animateDpAsState(
        targetValue = if (overlayVisible || showPostGameOverlay) 20.dp else 0.dp,
        animationSpec = tween(durationMillis = if (!overlayVisible || !showPostGameOverlay) 500 else 10),
        label = "BlurAnimation"
    )

    var startGame by remember { mutableStateOf(false) }
    val interactionSourcePre = remember { MutableInteractionSource() }
    val interactionSourcePost = remember { MutableInteractionSource() }

    LaunchedEffect(gameOver) {
        if (gameOver) {
            delay(800)
            showPostGameOverlay = true
        }
    }

    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(blurValue)
            ) {
                MenuBG(startDisappearing = startGame)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Box(
                        modifier = Modifier
                            .weight(0.20f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.weight(0.8f),
                                contentAlignment = Alignment.Center
                            ) {
                                ScoreComponent(viewModel.score)
                            }
                            Box(
//                                modifier = Modifier.weight(0.2f),
                                contentAlignment = Alignment.Center
                            ) {
                                Timer(
                                    startTimer = startGame,
                                    onTimeUp = {
                                        gameOver = true
                                        startGame = false
                                    }
                                )
                            }
                        }

                    }
                    Box(
                        modifier = Modifier
                            .weight(0.80f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ProblemKeypad(
                            problem = viewModel.problem,
                            solution = viewModel.solution,
                            usersAnswer = viewModel.currentAnswer,
                            textSize = if (viewModel.score.toString().length > 5) 20.sp else 35.sp,
                            previousProblem = viewModel.previousProblem,
                            onKeyPressed = { key ->
                                viewModel.onKeyPressed(key)
                            },
                            keypadReversed = false,
                            onBackspacePressed = {
                                viewModel.onAnswerDeletePress()
                            },
                        )
                    }
                }
            }
            // Pre-game overlay
            androidx.compose.animation.AnimatedVisibility(
                visible = overlayVisible,
                enter = fadeIn(
                    animationSpec = tween(500)
                ),
                exit = fadeOut(
                    animationSpec = tween(10)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x20000000))
                        .clickable(
                            interactionSource = interactionSourcePre,
                            indication = null,
                            onClick = { overlayVisible = false; startGame = true }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_timer_24),
                            contentDescription = "Pre-game timer",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 15.dp)
                        )
                        Text(
                            text = "Time Attack",
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 45.sp,
                        )

                        Text(
                            text = "Get as many right as you can in ${timeAttackTime.toInt() / 1000} seconds, or before the cubes disappear!",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "Tap anywhere to start",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            // Post-game overlay
            Box(
                modifier = if (gameOver) Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = interactionSourcePost,
                        indication = null,
                        onClick = {}
                    ) else Modifier
            ) {}
            PostGameOverlay(
                gameOver = showPostGameOverlay,
                score = viewModel.score,
                onPlayAgain = {
                    viewModel.resetState()
                    gameOver = false
                    showPostGameOverlay = false
                    startGame = true
                },
                onMenu = {
                    navController.navigate(NumSprintScreens.StarterScreen.name) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                innerPadding = innerPadding
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeAttackPreview() {
//    TimeAttack(false)
}