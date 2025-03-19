import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import kotlin.random.Random

// Data class for particles.
data class Particle(
    var x: Float,
    var y: Float,
    var speedY: Float,
    var driftX: Float,
    var life: Float // in seconds
)

@Composable
fun WitheringRedBackground() {
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    // Wipe progress goes from 0 (full red) to 1 (completely withered).
    var wipeProgress by remember { mutableStateOf(0f) }
    // List to store active particles.
    val particles = remember { mutableStateListOf<Particle>() }
    // Pre-generated jagged offsets for the top border of the red area.
    var jaggedOffsets by remember { mutableStateOf<List<Float>>(emptyList()) }
    // Record the start time for the 30-second animation.
    val startTime = remember { System.currentTimeMillis() }

    // Animation loop to update the wipe progress and particles.
    LaunchedEffect(canvasSize, jaggedOffsets) {
        if (canvasSize.width == 0 || canvasSize.height == 0) return@LaunchedEffect
        var lastFrameTime = withFrameNanos { it }
        while (wipeProgress < 1f) {
            val frameTime = withFrameNanos { it }
            val deltaTime = (frameTime - lastFrameTime) / 1_000_000_000f
            lastFrameTime = frameTime

            // Calculate wipe progress (30 seconds total).
            val elapsed = (System.currentTimeMillis() - startTime) / 1000f
            wipeProgress = (elapsed / 30f).coerceAtMost(1f)
            // Compute the base wipe Y position.
            val wipeY = wipeProgress * canvasSize.height.toFloat()

            // Update existing particles.
            val iterator = particles.listIterator()
            while (iterator.hasNext()) {
                val particle = iterator.next()
                particle.y -= particle.speedY * deltaTime
                particle.x += particle.driftX * deltaTime
                particle.life -= deltaTime
                if (particle.life <= 0f) {
                    iterator.remove()
                }
            }

            // Spawn new particles along the jagged border.
            repeat(5) {
                val randomX = Random.nextFloat() * canvasSize.width
                // Find the jagged offset for this x position.
                val spawnY = if (jaggedOffsets.isNotEmpty()) {
                    // Map the random x to an index in our jaggedOffsets list.
                    val index = ((randomX / canvasSize.width) * (jaggedOffsets.size - 1)).toInt()
                    wipeY + jaggedOffsets[index]
                } else {
                    wipeY
                }
                particles.add(
                    Particle(
                        x = randomX,
                        y = spawnY,
                        speedY = 100f + Random.nextFloat() * 100f, // 100-200 px/s upward
                        driftX = -50f + Random.nextFloat() * 100f,   // slight horizontal drift
                        life = 1.5f
                    )
                )
            }
            delay(16L) // roughly 60 FPS
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                canvasSize = size
                // Generate jagged offsets for a series of sample points along the top edge.
                val step = 20
                val count = (size.width / step) + 2 // +2 to ensure full coverage
                jaggedOffsets = List(count) { Random.nextFloat() * 20f - 10f } // Offsets between -10 and +10
            }
        ) {
            // Compute the base wipe Y position.
            val wipeY = wipeProgress * size.height

            // Build a path for the red region using a jagged top border.
            val path = Path().apply {
                if (jaggedOffsets.isNotEmpty()) {
                    // Start at the left edge using the first jagged offset.
                    moveTo(0f, wipeY + jaggedOffsets.first())
                    // Compute the step size based on the number of offsets.
                    val stepX = size.width / (jaggedOffsets.size - 1)
                    // Draw the jagged edge.
                    for (i in 1 until jaggedOffsets.size) {
                        lineTo(i * stepX, wipeY + jaggedOffsets[i])
                    }
                } else {
                    // Fallback to a straight line if no jagged offsets are available.
                    moveTo(0f, wipeY)
                    lineTo(size.width, wipeY)
                }
                // Close the path to form a polygon covering the lower part of the screen.
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            // Draw the red region defined by our jagged path.
            drawPath(path, Color.Red)

            // Draw particles.
            particles.forEach { particle ->
                val particleAlpha = (particle.life / 1.5f).coerceIn(0f, 1f)
                drawCircle(
                    color = Color.Red.copy(alpha = particleAlpha),
                    radius = 5f,
                    center = Offset(particle.x, particle.y)
                )
            }
        }
    }
}
