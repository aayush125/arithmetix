package com.example.numsprint

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numsprint.ui.components.FloatingSymbolsBackground
import com.example.numsprint.ui.components.Keypad
import com.example.numsprint.ui.components.ProblemComponent
import com.example.numsprint.ui.components.ProblemComponentV2
import com.example.numsprint.ui.components.ScoreBox
import com.example.numsprint.ui.components.UIButton
import com.example.numsprint.ui.theme.fontFamily_score
import com.example.numsprint.viewmodel.GameSessionViewModel
import kotlinx.coroutines.delay

@Composable
fun Endless() {
    val viewModel: GameSessionViewModel = viewModel()

    var targetScoreTextSize by remember { mutableStateOf(40f) }

    val scoreTextSize by animateFloatAsState(
        targetValue = targetScoreTextSize,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,  // Controls the bounciness
            stiffness = Spring.StiffnessMediumLow           // Controls the speed of animation
        ),
        label = "ScoreSize"
    )

    LaunchedEffect(viewModel.score) {
        if (viewModel.score > 0) {
            targetScoreTextSize = 60f
            delay(150)
            targetScoreTextSize = 40f
        }
    }

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
//                    Text(
//                        text = viewModel.score.toString(),
//                        fontSize = scoreTextSize.sp,
//                        fontFamily = fontFamily_score,
//                        fontWeight = FontWeight.W700,
//                        modifier = Modifier
//                            .background(Color.Yellow, CircleShape)
//                    )
                    ScoreBox(text = viewModel.score.toString(), textSize = scoreTextSize.sp)
                }
                Box(
                    modifier = Modifier
                        .weight(0.2f),
                    contentAlignment = Alignment.Center
                ) {
                    ProblemComponentV2(
                        viewModel.problem,
                        viewModel.solution,
                        viewModel.currentAnswer, 35.sp,
                        onAnimationFinish = { viewModel.cleanUpAndNext() },
                        isCorrect = viewModel.answerCorrect,
                        viewModel.previousProblem
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
                Box(modifier = Modifier.weight(0.15f)) {
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
                        buttonText = "Next",
                        enabled = (viewModel.currentAnswer.isNotBlank() && viewModel.currentAnswer.length == viewModel.solution.toString().length),
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        height = 50.dp,
                        width = 280.dp
                    )
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