package com.arithmetix.arithmetix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arithmetix.arithmetix.R
import com.arithmetix.arithmetix.ui.theme.fontFamily_timer
import com.arithmetix.arithmetix.utils.timeAttackTime
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun Timer(
    startTimer: Boolean = false,
    onTimeUp: () -> Unit = {}
) {
    val totalTime = timeAttackTime
    var timeLeft by remember { mutableLongStateOf(totalTime) }
    val updateDelay = 10L

    LaunchedEffect(startTimer) {
        if (startTimer) {
            val startTime = System.currentTimeMillis()
            val endTime = startTime + totalTime

            while (true) {
                val currentTime = System.currentTimeMillis()
                timeLeft = (endTime - currentTime).coerceAtLeast(0L)

                if (timeLeft <= 0) {
                    onTimeUp()
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
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = String.format(
                    Locale.US,
                    "%02d:%02d",
                    timeLeft / 1000,
                    (timeLeft % 1000) / 10
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(60.dp), // Fixed width to prevent shaking of the timer chip
                color = Color.Black,
                fontFamily = fontFamily_timer,
                fontWeight = FontWeight.ExtraBold,
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    Timer()
}