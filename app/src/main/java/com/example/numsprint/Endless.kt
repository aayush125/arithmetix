package com.example.numsprint

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numsprint.ui.components.CircularButton
import com.example.numsprint.ui.components.Keypad
import com.example.numsprint.ui.components.ProblemComponentV2
import com.example.numsprint.ui.components.ScoreComponent
import com.example.numsprint.viewmodel.GameSessionViewModel

@Composable
fun Endless() {
    val viewModel: GameSessionViewModel = viewModel()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box() {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .weight(0.2f),
                    contentAlignment = Alignment.Center,
                ) {
                    ScoreComponent(viewModel.score)
                }
                Box(
                    modifier = Modifier
                        .weight(0.2f),
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
                Column(
                    modifier = Modifier
                        .padding(bottom = innerPadding.calculateBottomPadding())
                        .weight(0.6f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(Modifier.weight(0.8f)) {
                        Keypad(
                            onKeyPressed = { key ->
                                viewModel.onKeyPressed(key)
                            },
                            false,
                            onBackspacePressed = {
                                viewModel.onAnswerDeletePress()
                            }
                        )
                    }
                    Box(Modifier.weight(0.2f)) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = { viewModel.solve() }) { Text(text = "Solve for me") }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 70.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                CircularButton(
                                    onClick = { viewModel.onNextClicked() },
                                    size = 45.dp,
                                    enabled = (viewModel.currentAnswer.isNotBlank() && viewModel.currentAnswer.length == viewModel.solution.toString().length),
                                )
                            }
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
    Endless()
}