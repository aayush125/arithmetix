package com.example.numsprint.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.numsprint.R
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun Timer(
    start: Boolean = false,
) {
    val totalTime = 20_000L
    var timeLeft by remember { mutableLongStateOf(totalTime) }
    val updateDelay =  10L

    LaunchedEffect(start) {
        if (start) {
            val startTime = System.currentTimeMillis()
            val endTime = startTime + totalTime

            while (true) {
                val currentTime = System.currentTimeMillis()
                timeLeft = (endTime - currentTime).coerceAtLeast(0L)

                if (timeLeft <= 0) {
                    // ontimerfinished here
                    break
                }
                delay(updateDelay) // update every updateDelay milliseconds
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(Color(0xffd4d3cf), RoundedCornerShape(20.dp))
            .padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_timer_24),
                contentDescription = "Timer Icon",
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = String.format(Locale.US, "%02d:%02d", timeLeft / 1000, (timeLeft % 1000) / 10),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    Timer()
}