package com.arithmetix.arithmetix

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arithmetix.arithmetix.model.GameModes
import com.arithmetix.arithmetix.ui.components.CircularButton
import com.arithmetix.arithmetix.ui.components.MenuBG
import com.arithmetix.arithmetix.ui.components.NavigationButton
import com.arithmetix.arithmetix.ui.components.ProblemKeypad
import com.arithmetix.arithmetix.ui.components.ScoreComponent
import com.arithmetix.arithmetix.viewmodel.GameSessionViewModel
import com.arithmetix.arithmetix.viewmodel.GameSessionViewModelFactory

@Composable
fun Endless(onBackNavigation: () -> Unit) {

    val viewModel: GameSessionViewModel = viewModel(
        factory = GameSessionViewModelFactory(GameModes.ENDLESS)
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box() {
            MenuBG()
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .weight(0.17f),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        ScoreComponent(viewModel.score)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 30.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        NavigationButton(onClick = onBackNavigation)
                    }

                }
                Box(
                    modifier = Modifier
                        .weight(0.71f)
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
                Box(
                    modifier = Modifier.weight(0.12f),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
//                        Button(onClick = { viewModel.solve() }) { Text(text = "Solve for me") }
//                        Box(modifier = Modifier.weight(0.8f)) {
//                            BottomStrip()
//                        }
                        Box(
                            modifier = Modifier
                                .weight(0.2f),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            CircularButton(
                                onClick = { viewModel.onNextClicked() },
                                size = 45.dp,
                                enabled = (viewModel.currentAnswer.isNotBlank() && viewModel.currentAnswer.length == viewModel.solution.toString().length),
                                modifier = Modifier.padding(end = 75.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndlessPreview() {
    Endless(onBackNavigation = { })
}