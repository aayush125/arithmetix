package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arithmetix.arithmetix.R

@Composable
fun CircularButton(
    onClick: () -> Unit,
    size: Dp,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) MaterialTheme.colorScheme.primary.copy(alpha = 0.7f) else Color.Transparent,
        animationSpec = if (isPressed) tween(durationMillis = 10)
        else tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "ButtonBackground"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isPressed) Color.White else MaterialTheme.colorScheme.onBackground,
        animationSpec = if (isPressed) tween(durationMillis = 10)
        else tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "ButtonBackground"
    )

    AnimatedVisibility(
        visible = enabled,
        enter = fadeIn(animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(1.dp)
                .background(backgroundColor, CircleShape)
                .size(size)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_forward_24),
                contentDescription = "NextButton_Endless",
                tint = iconColor,
                modifier = Modifier.size((size.value - 10).dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularButtonPreview() {
    CircularButton({}, size = 20.dp)
}