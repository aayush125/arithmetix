package com.arithmetix.arithmetix.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.viewmodel.PreviousProblem

@Composable
fun ProblemKeypad(
    problem: String, // current problem
    solution: Number,
    usersAnswer: String,
    textSize: TextUnit,
    previousProblem: PreviousProblem,
    onKeyPressed: (String) -> Unit,
    keypadReversed: Boolean,
    onBackspacePressed: () -> Unit,
    keypadModifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Box(modifier = Modifier.weight(0.2f)) {
            ProblemComponentV2(
                problem = problem,
                solution = solution,
                usersAnswer = usersAnswer,
                textSize = textSize,
                onAnimationFinish = {},
                isCorrect = null,
                previousProblem = previousProblem
            )
        }
        Box(
            modifier = Modifier
                .weight(0.8f)
        ) {
            Keypad(
                onKeyPressed = onKeyPressed,
                reversed = keypadReversed,
                onBackspacePressed = onBackspacePressed,
                modifier = keypadModifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProblemKeypadPreview() {
    ProblemKeypad(
        problem = "2 + 2",
        solution = 4,
        usersAnswer = "",
        textSize = 35.sp,
        previousProblem = PreviousProblem("1 + 3", "4", true),
        onKeyPressed = {},
        keypadReversed = false,
        onBackspacePressed = {},
    )
}