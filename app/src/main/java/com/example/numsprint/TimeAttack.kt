package com.example.numsprint

import android.graphics.Paint.Align
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numsprint.ui.components.Timer

@Composable
fun TimeAttack(overlayVisible: Boolean = false) {
    var overlayVisible by remember { mutableStateOf(overlayVisible) }

    val blurValue by animateDpAsState(
        targetValue = if (overlayVisible) 20.dp else 0.dp,
        animationSpec = tween(durationMillis = 500), label = "BlurAnimation"
    )

//    val blurMod = if (overlayVisible) Modifier.blur(20.dp) else Modifier

    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(blurValue)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Timer()
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (!overlayVisible) {
                            Button(onClick = { overlayVisible = true }) {
                                Text(text = "Show overlay")
                            }
                        }
                    }
                }
            }
            // Pre-game overlay
            androidx.compose.animation.AnimatedVisibility(
                visible = overlayVisible,
                enter = fadeIn(
                    animationSpec = tween(500)
                ),
                exit = fadeOut(
                    animationSpec = tween(500)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x20000000)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_timer_24),
                            contentDescription = "Pre-game timer",
                            tint = Color.Red,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 15.dp)
                        )
                        Text(
                            text = "Time Attack",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 45.sp,
                        )

                        Text(
                            text = "Get as many right as you can in 20 seconds!",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Tap anywhere to start",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )
                        Button(onClick = { overlayVisible = false }) {
                            Text(text = "Close overlay")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeAttackPreview() {
    TimeAttack(true)
}