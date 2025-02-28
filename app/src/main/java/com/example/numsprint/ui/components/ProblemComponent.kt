package com.example.numsprint.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numsprint.R
import com.example.numsprint.model.Operator
import com.example.numsprint.model.ProblemConfig
import com.example.numsprint.model.toExpressionString
import com.example.numsprint.ui.theme.fontFamily
import com.example.numsprint.ui.theme.fontFamily_keys
import com.example.numsprint.ui.theme.fontFamily_problem
import com.example.numsprint.utils.cornerRadius
import com.example.numsprint.viewmodel.PreviousProblem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProblemComponent(
    problem: String,
    solution: Number,
    usersAnswer: String,
    textSize: TextUnit,
    onAnimationFinish: () -> Unit,
    isCorrect: Boolean?,
) {
    var showOverlay by remember { mutableStateOf(false) }
    // Animatable for the checkmark rotation
    val rotX = remember { Animatable(0f) }

    LaunchedEffect(isCorrect) {
        if (isCorrect == true) {
            // Show the overlay (it will slide in from the bottom)
            showOverlay = true

            // Launch the spinning animation concurrently.
            // Both animations run over 800ms.
            coroutineScope {
                launch {
                    rotX.animateTo(
                        targetValue = -360f,
                        animationSpec = tween(600)
                    )
                }
            }
            // Optionally wait a bit then hide the overlay (which slides out)
            delay(300)
            showOverlay = false

            // Reset the rotation
            rotX.snapTo(0f)
            onAnimationFinish()
        }
    }

//    val correctBorderColor by animateColorAsState(
//        targetValue = if (isCorrect != null && isCorrect) Color.Green else Color.Transparent,
//        animationSpec = tween(durationMillis = 500),
//        label = "CorrectAnswerBorderColor",
//    )
//
//    LaunchedEffect(isCorrect) {
//        if (isCorrect == true) {
//            delay(500)
//            onAnimationFinish()
//        }
//    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Surface(
            shadowElevation = 9.dp, modifier = Modifier
                .padding(5.dp)
                .width(300.dp)
                .height(140.dp)
                .border(4.dp, Color.Transparent, RoundedCornerShape(cornerRadius)),
            shape = RoundedCornerShape(cornerRadius)
        ) {
            // Put the overlay in this box
            Box(contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = problem,
                        fontSize = textSize,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp),
                        letterSpacing = 4.sp,
                    )
                    Text(
                        text = buildString {
                            val solutionStr = solution.toString()
                            val parts = solutionStr.split(".")
                            val integerPart = parts[0]
                            val decimalPart =
                                if (parts.size > 1 && parts[1] != "0") parts[1] else ""

                            // Handle the integer part
                            val userChars = usersAnswer.take(integerPart.length).toCharArray()
                            repeat(integerPart.length) { index ->
                                if (index < userChars.size) {
                                    append(userChars[index])
                                } else {
                                    append("_")
                                }
                                if (index < integerPart.length - 1) {
                                    append(" ")
                                }
                            }

                            // Add decimal point and decimal part if needed
                            if (decimalPart.isNotEmpty()) {
                                append(" . ") // Spaces around the decimal point
                                repeat(decimalPart.length) {
                                    append("_")
                                    if (it < decimalPart.length - 1) {
                                        append(" ")
                                    }
                                }
                            }
                        },
                        fontSize = textSize,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                // Overlay here
                androidx.compose.animation.AnimatedVisibility(
                    visible = showOverlay,
                    enter = slideInVertically(
                        animationSpec = tween(600),
                        // The overlay starts off-screen at the bottom
                        initialOffsetY = { fullHeight -> fullHeight }
                    ),
                    exit = slideOutVertically(
                        animationSpec = tween(600),
                        targetOffsetY = { fullHeight -> -fullHeight }
                    ),
                    modifier = Modifier.matchParentSize()
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color(0x80000000)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Correct",
                            tint = Color.Green,
                            modifier = Modifier
                                .size(48.dp)
                                .graphicsLayer {
                                    rotationY = rotX.value
                                }
                        )
                    }
                }
            }
        }

    }

}

@Composable
fun ProblemComponentV2(
    problem: String, // current problem
    solution: Number,
    usersAnswer: String,
    textSize: TextUnit,
    onAnimationFinish: () -> Unit,
    isCorrect: Boolean?,
    previousProblem: PreviousProblem,
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                // Both problems in the same container for coordinated animation
                AnimatedContent(
                    targetState = Pair(previousProblem, problem),
                    transitionSpec = {
                        // Create a custom transition that:
                        // 1. Slides previous problem up and out while fading
                        // 2. Slides current problem in from bottom while fading in
                        ((slideInVertically { fullHeight -> fullHeight } + fadeIn()).togetherWith(
                            slideOutVertically { fullHeight -> -fullHeight } + fadeOut()))
                            .using(SizeTransform(clip = false))
                    },
                ) { (prevQuestionTarget, currentQuestionTarget) ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Previous problem
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (prevQuestionTarget.problem.isNotBlank()) {
                                    Text(
                                        text = "${prevQuestionTarget.problem} = ${prevQuestionTarget.solution}",
                                        fontSize = (textSize.value - 10).sp,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                                        fontFamily = fontFamily_problem
                                    )
                                    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                                        Icon(
                                            painter = if (prevQuestionTarget.wasCorrect) painterResource(
                                                R.drawable.baseline_check_24
                                            ) else painterResource(R.drawable.baseline_close_24),
                                            modifier = Modifier.padding(start = 5.dp),
                                            contentDescription = "Check Icon",
                                            tint = if (prevQuestionTarget.wasCorrect) Color.hsl(
                                                104f,
                                                0.73f,
                                                0.36f
                                            ) else Color.hsl(7f, 0.73f, 0.36f)
                                        )
                                    }
                                }
                            }

                        }

                        // Current problem
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "$currentQuestionTarget = ",
                                        fontSize = (textSize.value).sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                                        letterSpacing = 5.sp,
                                        fontFamily = fontFamily_problem
                                    )
                                    Text (
                                        text = buildString {
                                            val solutionStr = solution.toString()

                                            val userChars = usersAnswer.toCharArray()
                                            repeat(solutionStr.length) { index ->
                                                if (index < userChars.size) {
                                                    append(userChars[index])
                                                } else {
                                                    append("_")
                                                }
                                                if (index < solutionStr.length - 1) {
                                                    append(" ")
                                                }
                                            }
                                        },
                                        fontSize = (textSize.value).sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                                        letterSpacing = 5.sp,
                                        fontFamily = fontFamily_problem
                                    )

                                }
//                                Text(
//                                    text = buildString {
//                                        val solutionStr = solution.toString()
//
//                                        val userChars = usersAnswer.toCharArray()
//                                        repeat(solutionStr.length) { index ->
//                                            if (index < userChars.size) {
//                                                append(userChars[index])
//                                            } else {
//                                                append("_")
//                                            }
//                                            if (index < solutionStr.length - 1) {
//                                                append(" ")
//                                            }
//                                        }
//                                    },
//                                    fontSize = (textSize.value).sp,
//                                    fontWeight = FontWeight(450),
//                                    textAlign = TextAlign.Center,
//                                    color = MaterialTheme.colorScheme.onSurface,
//                                    modifier = Modifier.weight(1f),
//                                    letterSpacing = 5.sp,
//                                )

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
fun ProblemComponentPreview() {
    val problem = ProblemConfig(
        operands = listOf(3, 2, 3),
        operators = listOf(Operator.DIVIDE, Operator.MULTIPLY)
    )
//    ProblemComponent(problem.toExpressionString(), 2560, "40", 30.sp, {}, isCorrect = false)
    ProblemComponentV2(
        problem.toExpressionString(),
        2560,
        "40",
        30.sp,
        {},
        isCorrect = false,
        PreviousProblem("", "", false)
    )
}