package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.ui.theme.fontFamily_score
import com.arithmetix.arithmetix.utils.targetScoreFontSize
import kotlinx.coroutines.delay

@Composable
fun ScoreComponent(
    score: Int = 0,
) {
    var targetScoreTextSize by remember { mutableFloatStateOf(targetScoreFontSize) }

    val scoreTextSize by animateFloatAsState(
        targetValue = targetScoreTextSize,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,  // Controls the bounciness
            stiffness = Spring.StiffnessMediumLow           // Controls the speed of animation
        ),
        label = "ScoreSize"
    )

    LaunchedEffect(score) {
        if (score > 0) {
            targetScoreTextSize = 60f
            delay(150)
            targetScoreTextSize = targetScoreFontSize
        }
    }

    ScoreBox(text = score.toString(), textSize = scoreTextSize.sp)
}

@Composable
fun ScoreBox(
    text: String = "0",
    textSize: TextUnit = 40.sp,
    textFamily: FontFamily = fontFamily_score,
    textWeight: FontWeight = FontWeight.W700
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .size(70.dp),
            contentAlignment = Alignment.Center
        ) {}
        Text(
            text = text,
            fontSize = textSize,
            fontFamily = textFamily,
            fontWeight = textWeight
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreBoxPreview() {
//    ScoreBox()
    ScoreComponent(0)
}