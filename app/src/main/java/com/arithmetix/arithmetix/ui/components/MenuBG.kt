package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
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
    val squareSize = 250f
    // Define the topLeft positions of each square
    val squarePositions = listOf(
//        Offset(69f, 74f),
//        Offset(589f, 80f),
//        Offset(800f, 200f),
//        Offset(60f, 400f),
//        Offset(400f, 350f),
//        Offset(660f, 450f),
//        Offset(200f, 600f),
//        Offset(460f, 650f),
//        Offset(700f, 700f),
//        Offset(500f, 900f),
//        Offset(130f, 1000f),
//        Offset(780f, 1000f),
//        Offset(760f, 1320f),
//        Offset(337f, 1220f),
//        Offset(37f, 1220f),
//        Offset(137f, 1620f),
//        Offset(427f, 1490f),
//        Offset(327f, 1740f),
//        Offset(637f, 1750f),
//        Offset(267f, 1980f),
//        Offset(867f, 1980f),
//        Offset(687f, 2100f),
//        Offset(87f, 2180f),
//        Offset(487f, 2180f)

          Offset(69f, 74f),
        Offset(589f, 80f),
        Offset(800f, 400f),
        Offset(60f, 400f),
        Offset(400f, 350f),
        Offset(460f, 650f),
        Offset(150f, 700f),
//        Offset(150f, 600f),
        Offset(760f, 850f),
        Offset(450f, 960f),
        Offset(100f, 1000f),
        Offset(20f, 1310f),
        Offset(370f, 1290f),
        Offset(750f, 1190f),
        Offset(-10f, 1580f),
        Offset(320f, 1570f),
        Offset(650f, 1520f),
        Offset(750f, 1820f),
        Offset(30f, 1860f),
        Offset(390f, 1890f),
        Offset(20f, 2170f),
        Offset(330f, 2170f),
        Offset(670f, 2170f),
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

    val infiniteTransitionBG = rememberInfiniteTransition(label = "BGRotation")
    val rotationAngleBG by infiniteTransitionBG.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "BGRotation"
    )

    val infiniteTransitionScale = rememberInfiniteTransition(label = "BGScale")
    val scaleCanvas by infiniteTransitionScale.animateFloat(
        initialValue = 1.2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "BGScale"
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
        .graphicsLayer {
            translationY = offsetY.value
//            rotationZ = rotationAngleBG
            scaleX = scaleCanvas
            scaleY = scaleCanvas
        }
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