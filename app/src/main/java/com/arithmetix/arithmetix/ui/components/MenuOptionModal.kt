package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.R
import com.arithmetix.arithmetix.model.AppThemeNames
import com.arithmetix.arithmetix.ui.theme.BlueColorScheme
import com.arithmetix.arithmetix.ui.theme.DarkColorScheme
import com.arithmetix.arithmetix.ui.theme.LightColorScheme
import com.arithmetix.arithmetix.utils.modalCornerRadius

@Composable
fun MenuOptionModal(
    title: String,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val interactionSourceCloseButton = remember { MutableInteractionSource() }
    val isPressed by interactionSourceCloseButton.collectIsPressedAsState()

    val closeButtonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.7f else 1f,
        animationSpec = tween(50),
        label = "Close Button Scale",
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClose
                ),
        ) {}
        Surface(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(modalCornerRadius.dp)
                )
                .width(330.dp)
                .height(430.dp),
            shadowElevation = modalCornerRadius.dp,
            shape = RoundedCornerShape(modalCornerRadius.dp)
        ) {
            Box(
                modifier = Modifier.padding(25.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_close_24),
                                contentDescription = "Modal close icon",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clickable(
                                        interactionSource = interactionSourceCloseButton,
                                        indication = null,
                                        onClick = onClose
                                    )
                                    .scale(closeButtonScale)
                            )
                        }
                    }
                    content()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuOptionModalPreview() {
    MenuOptionModal(
        title = "",
        onClose = {}
    ) { }
}