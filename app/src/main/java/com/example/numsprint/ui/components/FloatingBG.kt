package com.example.numsprint.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun FloatingSymbolsBackground(
    modifier: Modifier = Modifier,
    symbolCount: Int = 50,  // Increased from 30 to 50 for higher density
    symbols: List<String> = listOf("+", "-", "×", "÷", "=", "%"),
    // Use MaterialTheme color with content alpha for automatic light/dark mode adaptation
    symbolColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
) {
    // Create and remember a list of floating symbols with random properties
    val floatingSymbols = remember {
        List(symbolCount) {
            FloatingSymbol(
                symbol = symbols.random(),
                x = Random.nextFloat() * 1000,
                y = Random.nextFloat() * 2000,
                speed = 1f + Random.nextFloat() * 3f,
                direction = Random.nextFloat() * 360f,
                size = 30f + Random.nextFloat() * 70f,
                alpha = 0.4f + Random.nextFloat() * 0.6f
            )
        }
    }

    // Animation state to trigger recomposition
    val animationState = remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while(true) {
            withFrameMillis { frameTime ->
                // Update animation state to force recomposition
                animationState.value = frameTime.toFloat()
            }
        }
    }

    // Get layout size
    var size by remember { mutableStateOf(IntSize.Zero) }

    Canvas(modifier = modifier
        .fillMaxSize()
        .onSizeChanged { size = it }
    ) {
        // The animation state forces this to be recalculated on each frame
        animationState.value

        // Convert Compose Color to Android Color
        val androidColor = android.graphics.Color.argb(
            symbolColor.alpha,
            symbolColor.red,
            symbolColor.green,
            symbolColor.blue
        )

        // For each symbol, update position and draw
        floatingSymbols.forEach { symbol ->
            // Update position based on speed and direction
            val radians = Math.toRadians(symbol.direction.toDouble())
            symbol.x += symbol.speed * cos(radians).toFloat()
            symbol.y += symbol.speed * sin(radians).toFloat()

            // Check boundaries and reset if needed
            if (symbol.x < -100 || symbol.x > size.width + 100 ||
                symbol.y < -100 || symbol.y > size.height + 100) {
                symbol.x = Random.nextFloat() * size.width
                symbol.y = Random.nextFloat() * size.height
                symbol.direction = Random.nextFloat() * 360f
            }

            // Draw the symbol
            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    color = androidColor
                    alpha = (symbol.alpha * 255).toInt()
                    textSize = symbol.size
                    isAntiAlias = true
                }
                drawText(symbol.symbol, symbol.x, symbol.y, paint)
            }
        }
    }
}

// Data class to represent each floating symbol
data class FloatingSymbol(
    val symbol: String,
    var x: Float,
    var y: Float,
    val speed: Float,
    var direction: Float,
    val size: Float,
    val alpha: Float
)
@Preview(showBackground = true)
@Composable
fun FloatingSymbolBGPreview() {
//    FloatingSymbolsBackground()
}
