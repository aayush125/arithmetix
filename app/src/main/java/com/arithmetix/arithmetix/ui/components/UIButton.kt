package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.utils.cornerRadius

@Composable
fun UIButton(
    onClick: () -> Unit,
    buttonText: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    height: Dp = 30.dp,
    width: Dp = 100.dp,
    fontSize: TextUnit = 24.sp
) {
    val colors = MaterialTheme.colorScheme

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) colors.primary else Color.Transparent,
        animationSpec = if (isPressed) tween(durationMillis = 10)
        else tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "ButtonBackground"
    )

    val textColor by animateColorAsState(
        targetValue = if (isPressed) colors.onPrimary else colors.onBackground,
        animationSpec = if (isPressed) tween(durationMillis = 10)
        else tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "TextColor"
    )

//    val borderColor by animateColorAsState(
//        targetValue = if (isPressed) Color.Transparent else colors.surface,
//        animationSpec = if (isPressed) tween(durationMillis = 10)
//        else tween(durationMillis = 100, easing = LinearOutSlowInEasing),
//        label = "BorderColor"
//    )
    val borderColorOpacity by animateFloatAsState(
        targetValue = if (isPressed) 0f else 1f,
        animationSpec = if (isPressed) tween(durationMillis = 10) else tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        ),
        label = "BorderColorOpacity"
    )

    val buttonScaleSpringAnim by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,  // Controls the bounciness
            stiffness = Spring.StiffnessMedium              // Controls the speed of animation
        ),
        label = "ButtonSpringAnimation"
    )


    AnimatedVisibility(
        visible = enabled,
        enter = fadeIn(animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)),
        modifier = Modifier.scale(buttonScaleSpringAnim)
    ) {
        Box(
            modifier = Modifier
                .then(modifier)
                .padding(10.dp)
                .background(backgroundColor, RoundedCornerShape(cornerRadius))
//                .border(2.dp, borderColor, RoundedCornerShape(cornerRadius))
                .border(
                    2.dp, colors.surface.copy(alpha = borderColorOpacity), RoundedCornerShape(
                        cornerRadius
                    )
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UIButtonPreview() {
    UIButton({}, buttonText = "Next", true, height = 30.dp, width = 100.dp, fontSize = 15.sp)
}