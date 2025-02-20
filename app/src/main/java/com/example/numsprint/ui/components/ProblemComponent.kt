package com.example.numsprint.ui.components

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numsprint.model.Operator
import com.example.numsprint.model.ProblemConfig
import com.example.numsprint.model.toExpressionString
import com.example.numsprint.utils.cornerRadius
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
                        modifier = Modifier.padding(10.dp)
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

@Preview(showBackground = true)
@Composable
fun ProblemComponentPreview() {
    val problem = ProblemConfig(
        operands = listOf(3, 2, 3),
        operators = listOf(Operator.DIVIDE, Operator.MULTIPLY)
    )
    ProblemComponent(problem.toExpressionString(), 2560, "40", 30.sp, {}, isCorrect = false)
}