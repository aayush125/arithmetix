package com.example.numsprint

import android.graphics.Paint.Align
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import com.example.numsprint.ui.components.Keypad
import com.example.numsprint.ui.components.ProblemComponentV2
import com.example.numsprint.ui.components.ScoreBox
import com.example.numsprint.ui.components.ScoreComponent
import com.example.numsprint.ui.components.Timer
import com.example.numsprint.utils.targetScoreFontSize
import com.example.numsprint.viewmodel.GameSessionViewModel
import kotlinx.coroutines.delay

@Composable
fun TimeAttack(overlayVisiblePreview: Boolean = false) {
    val viewModel: GameSessionViewModel = viewModel()

    var overlayVisible by remember { mutableStateOf(true) }

    val blurValue by animateDpAsState(
        targetValue = if (overlayVisible) 20.dp else 0.dp,
        animationSpec = tween(durationMillis = if (!overlayVisible) 500 else 10),
        label = "BlurAnimation"
    )

    var startGame by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(blurValue)
            ) {
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
                                Timer(startGame)
                            }
                        }

                    }
                    Box(
                        modifier = Modifier
                            .weight(0.80f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
//                            if (!overlayVisible) {
//                                Button(onClick = { overlayVisible = true }) {
//                                    Text(text = "Show overlay")
//                                }
//                                Button(onClick = { viewModel.incrementScore()}) {
//                                    Text(text = "Increment score")
//                                }
//                            }
                            Box(
                                modifier = Modifier.fillMaxSize().weight(0.3f),
                                contentAlignment = Alignment.Center
                            ) {
                                ProblemComponentV2(
                                    viewModel.problem,
                                    viewModel.solution,
                                    viewModel.currentAnswer,
                                    if (viewModel.score.toString().length > 5) 20.sp else 35.sp,
                                    onAnimationFinish = { viewModel.cleanUpAndNext() },
                                    isCorrect = viewModel.answerCorrect,
                                    viewModel.previousProblem
                                )
                            }
                            Box(
                                modifier = Modifier.fillMaxSize().weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Keypad(
                                    onKeyPressed = { key ->
                                        viewModel.onKeyPressed(key)
                                    },
                                    false,
                                    onBackspacePressed = {
                                        viewModel.onNextClicked()
                                    }
                                )
                            }
                        }
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
                            interactionSource = interactionSource,
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
                            tint = Color.Red,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 15.dp)
                        )
                        Text(
                            text = "Time Attack",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 45.sp,
                        )

                        Text(
                            text = "Get as many right as you can in 20 seconds!",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Tap anywhere to start",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )
//                        Button(onClick = { overlayVisible = false }) {
//                            Text(text = "Close overlay")
//                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeAttackPreview() {
    TimeAttack(false)
}