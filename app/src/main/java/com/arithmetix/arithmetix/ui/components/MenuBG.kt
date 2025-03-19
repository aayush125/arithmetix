package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arithmetix.arithmetix.utils.timeAttackTime

@Composable
fun MenuBG(startDisappearing: Boolean = false) {
    val squareSize = 150f
    // Define the topLeft positions of each square
    val squarePositions = listOf(
        Offset(69f, 74f),
        Offset(800f, 200f),
        Offset(400f, 350f),
        Offset(200f, 600f),
        Offset(700f, 700f),
        Offset(500f, 900f),
        Offset(130f, 1000f),
        Offset(780f, 1000f),
        Offset(760f, 1320f),
        Offset(337f, 1220f),
        Offset(137f, 1620f),
        Offset(637f, 1750f),
        Offset(267f, 1980f),
        Offset(687f, 2100f)
    )

    val colors = MaterialTheme.colorScheme

    // Animate a rotation angle that goes from 0 to 360 degrees repeatedly.
    val infiniteTransition = rememberInfiniteTransition(label = "BackgroundSquareRotation")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "BackgroundSquareRotation"
    )

    val configuration = LocalConfiguration.current
    val screenHeightPx = with(LocalDensity.current) {
        configuration.screenHeightDp.dp.toPx()
    }

    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(startDisappearing) {
        if (startDisappearing) {
            if (offsetY.value != 0f) offsetY.snapTo(0f)
            offsetY.animateTo(
                targetValue = screenHeightPx,
                animationSpec = tween(durationMillis = timeAttackTime.toInt(), easing = LinearEasing)
            )
        }
    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .graphicsLayer { translationY = offsetY.value }
    ) {
        // Loop over each square position
        for (pos in squarePositions) {
            // Rotate around the center of the square
            rotate(
                degrees = rotationAngle,
                pivot = Offset(pos.x + squareSize / 2, pos.y + squareSize / 2)
            ) {
                drawRect(
                    color = colors.onBackground.copy(alpha = 0.08f),
                    topLeft = pos,
                    size = Size(squareSize, squareSize)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MenuBGPreview() {
    MenuBG()
}