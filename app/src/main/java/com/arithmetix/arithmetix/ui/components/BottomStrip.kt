package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arithmetix.arithmetix.R


@Composable
fun BottomStrip() {
    var settingsSliderVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val settingsBGAlpha by animateFloatAsState(
        targetValue = if (settingsSliderVisible) 1f else 0f,
        animationSpec = tween(300),
        label = "SettingsBG"
    )
    val settingsIconRotation by animateFloatAsState(
        targetValue = if (settingsSliderVisible) 90f else 0f,
        animationSpec = tween(300),
        label = "SettingsIconRotation"
    )
    Row(
        modifier = Modifier
            .padding(10.dp)
            .border(2.dp, Color.Blue)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = settingsBGAlpha))
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_settings_24),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "SettingsIcon",
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { settingsSliderVisible = !settingsSliderVisible }
                    )
                    .rotate(settingsIconRotation)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Difficulties",
            )
            androidx.compose.animation.AnimatedVisibility(
                visible = settingsSliderVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                ),
                modifier = Modifier.matchParentSize()
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = settingsBGAlpha))
                        .graphicsLayer { alpha = settingsBGAlpha }
                ) {
                    Text(
                        text = "Theme"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomStripPreview() {
    BottomStrip()
}