package com.example.numsprint.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numsprint.ui.theme.fontFamily_score

@Composable
fun ScoreBox(
    modifier: Modifier = Modifier,
    text: String = "0",
    textSize: TextUnit = 40.sp,
    textFamily: FontFamily = fontFamily_score,
    textWeight: FontWeight = FontWeight.W700
) {
    Box(
        modifier = Modifier
            .then(modifier)
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
    ScoreBox()
}