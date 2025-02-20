package com.example.numsprint.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.numsprint.R
import com.example.numsprint.utils.cornerRadius

@Composable
fun BackspaceKey(modifier: Modifier, onClick: () -> Unit) {
    // Track the press state using an interaction source.
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animate the background color based on the press state.
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isPressed) Color(0xffb30000) else Color.Red,
        label = "BackspaceBackground"
    )

    // Animate the icon tint color based on the press state.
    val animatedIconColor by animateColorAsState(
        targetValue = if (isPressed) Color.LightGray else Color.White,
        label = "BackspaceIcon"
    )

    Box(
        modifier = Modifier
            .then(modifier)
            // Apply a clickable modifier with the interaction source.
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            // Use the animated background color.
            .background(color = animatedBackgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_backspace_24),
            contentDescription = "Backspace",
            // Use the animated icon color.
            tint = animatedIconColor
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BackspaceKeyPreview() {
    BackspaceKey(modifier = Modifier.padding(15.dp).size(70.dp)) {}
}