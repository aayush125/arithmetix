package com.example.numsprint

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numsprint.viewmodel.PreviousProblem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ProblemComponentTest(
    currentProblem: String = "2 x 3 + 4",
    solution: Number = 10,
    usersAnswer: String = "4",
    textSize: TextUnit = 35.sp,
    onAnimationFinish: () -> Unit = {},
    isCorrect: Boolean? = false,
    previousProblem: PreviousProblem = PreviousProblem("1 + 2", "3", true),
    nextProblem: String = "3 x 14 + 2",
) {
    val previousTextSize = (textSize.value - 6).sp
    val gradientHeight = 50.dp
    val gradientHeightPx = with(LocalDensity.current) { gradientHeight.toPx() }

    var currentQuestion by remember { mutableStateOf("2 x 3 + 4 = 4 _") }
    var prevQuestion by remember { mutableStateOf("2 x 4 + 5 = 13") }
    var nextQuestion by remember { mutableStateOf("Next problem") }

    LaunchedEffect(isCorrect) {
        if (isCorrect == true) {
            prevQuestion = currentQuestion
            currentQuestion = nextQuestion
        }
    }

    Box(
        modifier = Modifier
            .height(100.dp)
            .width(300.dp)
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
                    targetState = Pair(prevQuestion, currentQuestion),
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
                            Text(
                                text = prevQuestionTarget,
                                fontSize = previousTextSize,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // Current problem
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = currentQuestionTarget,
                                fontSize = (textSize.value).sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }


//        Box(
//            modifier = Modifier
//                .matchParentSize()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.background.copy(alpha = 0.05f),
//                            MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
//                            MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
//                            MaterialTheme.colorScheme.background.copy(alpha = 0.25f),
//                            MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
//                            MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
//                            MaterialTheme.colorScheme.background.copy(alpha = 0.05f),
//                            Color.Transparent
//                        ),
//                        startY = 0f,
//                        endY = gradientHeightPx
//                    )
//                )
//        )
    }
}

@Composable
fun TestScreenV2() {
    var trigger by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ProblemComponentTest(isCorrect = trigger)
            Button({ trigger = !trigger }) { Text(text = "Mark correct") }
        }
//        Row {
//            var count by remember { mutableIntStateOf(0) }
//            Button(onClick = { count++ }) {
//                Text("Add")
//            }
//            AnimatedContent(
//                targetState = count,
//                label = "animated content"
//            ) { targetCount ->
//                // Make sure to use `targetCount`, not `count`.
//                Text(text = "Count: $targetCount")
//            }
//        }
//        Column {
//            var change by remember { mutableStateOf(false) }
//            AnimatedContent(
//                targetState = change,
//                label = "test",
//                transitionSpec = {
//                    slideInVertically { fullHeight -> fullHeight } + fadeIn() togetherWith slideOutVertically { fullHeight -> -fullHeight } + fadeOut()
//                }
//            ) { targetChange ->
//                Text(
//                    text = "Change: $targetChange"
//                )
//            }
//            Button({ change = !change }) { Text(text = "Click") }
//        }
    }
}

@Composable
fun TestScreen() {
    var isCorrect by remember { mutableStateOf(false) }
    var showOverlay by remember { mutableStateOf(false) }
    // Animatable for the checkmark rotation
    val rotX = remember { Animatable(0f) }

    LaunchedEffect(isCorrect) {
        // Show the overlay (it will slide in from the bottom)
        showOverlay = true

        // Launch the spinning animation concurrently.
        // Both animations run over 800ms.
        coroutineScope {
            launch {
                rotX.animateTo(
                    targetValue = -360f,
                    animationSpec = tween(800)
                )
            }
        }
        // Optionally wait a bit then hide the overlay (which slides out)
        delay(300)
        showOverlay = false

        // Reset the rotation
        rotX.snapTo(0f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .width(150.dp)
                .height(60.dp)
                .clip(RectangleShape),
            contentAlignment = Alignment.Center
        ) {
            Surface(shadowElevation = 9.dp, modifier = Modifier.fillMaxSize()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan)
                ) {
                    Text(
                        text = "Flips",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            }
            // Overlay with slide-in/slide-out transition
            androidx.compose.animation.AnimatedVisibility(
                visible = showOverlay,
                enter = slideInVertically(
                    animationSpec = tween(800),
                    // The overlay starts off-screen at the bottom
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    animationSpec = tween(800),
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
                        imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                        contentDescription = if (isCorrect) "Correct" else "Wrong",
                        tint = if (isCorrect) Color.Green else Color.Red,
                        modifier = Modifier
                            .size(48.dp)
                            .graphicsLayer {
                                rotationY = rotX.value
                            }
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Button(onClick = { isCorrect = true }) {
                Text(text = "Correct")
            }
            Button(onClick = { isCorrect = false }) {
                Text(text = "Incorrect")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    TestScreenV2()
}