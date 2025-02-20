package com.example.numsprint

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numsprint.ui.components.Keypad
import com.example.numsprint.ui.components.ProblemComponent
import com.example.numsprint.ui.components.UIButton
import com.example.numsprint.viewmodel.GameSessionViewModel

@Composable
fun Endless() {
    val viewModel: GameSessionViewModel = viewModel()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = viewModel.score.toString(),
                    fontSize = 50.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                ProblemComponent(
                    viewModel.problem,
                    viewModel.solution,
                    viewModel.currentAnswer, 35.sp,
                    onAnimationFinish = { viewModel.cleanUpAndNext() },
                    isCorrect = viewModel.answerCorrect
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
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
            Box(modifier = Modifier.weight(0.1f)) {
//                Button(
//                    onClick = {
//                        viewModel.onNextClicked()
//                    },
//                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
//                    enabled = viewModel.currentAnswer.isNotBlank()
//                ) {
//                    Text(text = "Next")
//                }
                UIButton(
                    { viewModel.onNextClicked() },
                    "Next",
                    enabled = (viewModel.currentAnswer.isNotBlank() && viewModel.currentAnswer.length == viewModel.solution.toString().length),
                    height = 50.dp,
                    width = 280.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndlessPreview() {
    Endless()
}