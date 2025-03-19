package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.ui.theme.fontFamily_keys

@Composable
fun KeypadKey(
    text: String,
    fontSize: TextUnit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    index: Int
) {
    val colors = MaterialTheme.colorScheme

    var startAnimation by remember { mutableStateOf(false) }

    val initialOffset = IntOffset(0, 800)
    // Stagger the delay based on the key index
    val animationDelay = index * 20 // Adjust the delay as needed
    val animatedOffset by animateIntOffsetAsState(
        targetValue = if (startAnimation) IntOffset(0, 0) else initialOffset,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = animationDelay,
            easing = LinearOutSlowInEasing
        ),
        label = "KeyOffsetAnimation"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) colors.primary else Color.Transparent,
        animationSpec = if (isPressed) {
            tween(durationMillis = 10) // Immediate fade-in
        } else {
            tween(durationMillis = 500, easing = LinearOutSlowInEasing) // Slow fade-out
        },
        label = "ButtonBackground"
    )

    val textSize by animateFloatAsState(
        targetValue = if (isPressed) (fontSize.value - 12) else fontSize.value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,  // Controls the bounciness
            stiffness = Spring.StiffnessMedium              // Controls the speed of animation
        ),
        label = "TextSize"
    )


    val textColor by animateColorAsState(
        targetValue = if (isPressed) Color.White else colors.onSurface,
        animationSpec = if (isPressed) {
            tween(durationMillis = 10) // Immediate fade-in
        } else {
            tween(durationMillis = 500, easing = LinearOutSlowInEasing) // Slow fade-out
        },
        label = "TextBackground"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isPressed) Color.Transparent else Color.LightGray,
        animationSpec = if (isPressed) {
            tween(durationMillis = 10)
        } else {
            tween(durationMillis = 500, easing = LinearOutSlowInEasing)
        },
        label = "BorderColor"
    )

    Box(
        modifier = Modifier
            .then(modifier)
//            .background(backgroundColor, CircleShape)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .offset {
                animatedOffset
            },
//            .border(width = 0.8.dp, color = borderColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = textSize.sp,
            fontFamily = fontFamily_keys,
            fontWeight = FontWeight(900),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeypadKeyPreview() {
    KeypadKey(text = "8", fontSize = 50.sp, onClick = {}, modifier = Modifier.size(80.dp), 0)
}