package com.example.numsprint

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TestScreen() {
    var isCorrect by remember { mutableStateOf(false) }
    var showOverlay by remember { mutableStateOf(false) }
    // Animatable for the checkmark rotation
    val rotX = remember { Animatable(0f) }

    LaunchedEffect(isCorrect) {
        // Show the overlay (it will slide in from the bottom)
        showOverlay = true

        // Launch the spinning animation concurrently.
        // Both animations run over 800ms.
        coroutineScope {
            launch {
                rotX.animateTo(
                    targetValue = -360f,
                    animationSpec = tween(800)
                )
            }
        }
        // Optionally wait a bit then hide the overlay (which slides out)
        delay(300)
        showOverlay = false

        // Reset the rotation
        rotX.snapTo(0f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .width(150.dp)
                .height(60.dp)
                .clip(RectangleShape),
            contentAlignment = Alignment.Center
        ) {
            Surface(shadowElevation = 9.dp, modifier = Modifier.fillMaxSize()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan)
                ) {
                    Text(
                        text = "Flips",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            }
            // Overlay with slide-in/slide-out transition
            androidx.compose.animation.AnimatedVisibility(
                visible = showOverlay,
                enter = slideInVertically(
                    animationSpec = tween(800),
                    // The overlay starts off-screen at the bottom
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    animationSpec = tween(800),
                    targetOffsetY = { fullHeight -> -fullHeight }
                ),
                modifier = Modifier.matchParentSize()
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color(0x80000000)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                        contentDescription = if (isCorrect) "Correct" else "Wrong",
                        tint = if (isCorrect) Color.Green else Color.Red,
                        modifier = Modifier
                            .size(48.dp)
                            .graphicsLayer {
                                rotationY = rotX.value
                            }
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Button(onClick = { isCorrect = true }) {
                Text(text = "Correct")
            }
            Button(onClick = { isCorrect = false }) {
                Text(text = "Incorrect")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    TestScreen()
}