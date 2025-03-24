package com.arithmetix.arithmetix.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.ui.theme.fontFamily_score
import com.arithmetix.arithmetix.utils.postGameOverlayButtonOffset
import com.arithmetix.arithmetix.utils.postGameTextTargetOffset
import kotlinx.coroutines.delay

@Composable
fun OverlayButton(
    onClick: () -> Unit,
    text: String,
    size: TextUnit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val animateButtonSpring by animateFloatAsState(
        targetValue = if (isPressed) (size.value - 5) else size.value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,  // Controls the bounciness
            stiffness = Spring.StiffnessMedium              // Controls the speed of animation
        ),
        label = "OverlaySpringyButton"
    )

    Box(
        modifier = Modifier
            .then(modifier)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
                enabled = enabled,
            )
    ) {
        Text(
            text = text,
            fontSize = animateButtonSpring.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

enum class AnimationStates {
    INITIAL,
    HEAD_SLIDE_UP,
    SHOW_SCORE,
    BEFORE_BUTTON_DELAY,
    SHOW_REPLAY_BUTTON,
    SHOW_MENU_BUTTON,
}

@Composable
fun PostGameOverlay(
    gameOver: Boolean = false,
    score: Int,
    onPlayAgain: () -> Unit,
    onMenu: () -> Unit,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    // InteractionSource to block all interactions with the game elements (e.g. keypad)
    val interactionSource = remember { MutableInteractionSource() }

    var animationState by remember { mutableStateOf(AnimationStates.INITIAL) }

    var showScore by remember { mutableStateOf(false) }
    var isResetting by remember { mutableStateOf(false) }

    var headOffsetY by remember { mutableIntStateOf(0) }

    // Using -1 here so that the increased font size spring animation for the score runs when this gets updated to 0 by the 'score' parameter
    var scoreInternal by remember { mutableIntStateOf(-1) }
    var targetScoreTextSize by remember { mutableFloatStateOf(120f) }

    val textOffsetHead by animateIntOffsetAsState(
        targetValue = if (gameOver) IntOffset(0, headOffsetY) else IntOffset(0, 500),
        animationSpec = tween(700),
        label = "OffsetAnimationPostGameOverlay",
        finishedListener = {
            Log.d("Header animation", "Header animation playing")
            if (animationState == AnimationStates.INITIAL && !isResetting) {
                animationState = AnimationStates.HEAD_SLIDE_UP
            }
            isResetting = false
        }
    )

    val playAgainButtonAlpha by animateFloatAsState(
        targetValue = if (animationState >= AnimationStates.SHOW_REPLAY_BUTTON) 1f else 0f,
        animationSpec = tween(600),
        label = "PlayAgainButtonAlpha",
        finishedListener = {
            if (animationState == AnimationStates.SHOW_REPLAY_BUTTON) animationState =
                AnimationStates.SHOW_MENU_BUTTON
        }
    )

    val menuButtonAlpha by animateFloatAsState(
        targetValue = if (animationState >= AnimationStates.SHOW_MENU_BUTTON) 1f else 0f,
        animationSpec = tween(600),
        label = "PlayAgainButtonAlpha",
    )

    val scoreAnimation by animateIntAsState(
        targetValue = scoreInternal,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "ScoreIntAnimation",
        finishedListener = {
            targetScoreTextSize = 120f
            if (animationState == AnimationStates.SHOW_SCORE) animationState =
                AnimationStates.BEFORE_BUTTON_DELAY
        }
    )

    val scoreTextSize by animateFloatAsState(
        targetValue = targetScoreTextSize,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,  // Controls the bounciness
            stiffness = Spring.StiffnessMediumLow           // Controls the speed of animation
        ),
        label = "ScoreSize"
    )

    val scoreAlpha by animateFloatAsState(
        targetValue = if (showScore) 1f else 0f,
        animationSpec = tween(300),
        label = "ScoreTransparencyAnimation"
    )

    LaunchedEffect(animationState) {
        if (animationState == AnimationStates.HEAD_SLIDE_UP) {
            Log.d("Delay", "Setting delay now")
            delay(300)
            Log.d("Offset", "Setting head offset now")
            headOffsetY = postGameTextTargetOffset
            delay(180)
            targetScoreTextSize = 200f
            scoreInternal = score
            animationState = AnimationStates.SHOW_SCORE
            showScore = true
        } else if (animationState == AnimationStates.BEFORE_BUTTON_DELAY) {
            delay(500)
            animationState = AnimationStates.SHOW_REPLAY_BUTTON
        }
    }

    AnimatedVisibility(
        visible = gameOver,
        enter = fadeIn(
            animationSpec = tween(700)
        ),
        exit = fadeOut(
            animationSpec = tween(10)
        )
    ) {
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {}
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Time's up!",
                    modifier = Modifier.offset { textOffsetHead },
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = scoreAnimation.toString(),
                    modifier = Modifier
                        .offset { IntOffset(0, postGameTextTargetOffset + 420) }
                        .alpha(scoreAlpha)
                        .scale(scoreAlpha),
                    fontSize = scoreTextSize.sp,
                    fontFamily = fontFamily_score,
                    fontWeight = FontWeight.W800,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Box(
                    modifier = Modifier
                        .offset(0.dp, postGameOverlayButtonOffset.dp)
                        .alpha(playAgainButtonAlpha)
                ) {
                    OverlayButton(
                        onClick = {
                            // Reset state of this overlay
                            isResetting = true
                            animationState = AnimationStates.INITIAL
                            showScore = false
                            headOffsetY = 0
                            scoreInternal = -1
                            targetScoreTextSize = 120f
                            onPlayAgain()

                        },
                        text = "Play Again",
                        size = 30.sp,
                        enabled = animationState >= AnimationStates.SHOW_REPLAY_BUTTON
                    )
                }
                Box(
                    modifier = Modifier
                        .alpha(menuButtonAlpha)
                        .fillMaxSize()
                        .padding(30.dp)
                ) {
//                OverlayButton(
//                    onClick = {
//                        onMenu()
//                    },
//                    text = "Menu",
//                    size = 30.sp,
//                    enabled = animationState >= AnimationStates.SHOW_MENU_BUTTON
//                )
                    NavigationButton(onClick = onMenu)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostGameOverlayPreview() {
    PostGameOverlay(gameOver = true, 0, {}, {})
}