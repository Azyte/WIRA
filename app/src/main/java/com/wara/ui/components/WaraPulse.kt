package com.wara.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wara.ui.theme.TrustBlue

@Composable
fun WaraPulse(
    modifier: Modifier = Modifier,
    onSpeechStart: () -> Unit,
    onSpeechEnd: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    // Infinite transition for the pulse waves when pressed
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    
    // Wave 1
    val wave1Radius by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = if (isPressed) 200f else 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave1"
    )
    val wave1Alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isPressed) 0f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave1Alpha"
    )

    // Wave 2
    val wave2Radius by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = if (isPressed) 200f else 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing, delayMillis = 300),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave2"
    )
    val wave2Alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isPressed) 0f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing, delayMillis = 300),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave2Alpha"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        // Pulse Canvas (Drawn only when pressed, highly optimized)
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (isPressed) {
                drawCircle(
                    color = TrustBlue.copy(alpha = wave1Alpha * 0.4f),
                    radius = wave1Radius,
                    style = Stroke(width = 6.dp.toPx())
                )
                drawCircle(
                    color = TrustBlue.copy(alpha = wave2Alpha * 0.4f),
                    radius = wave2Radius,
                    style = Stroke(width = 6.dp.toPx())
                )
            }
        }

        // Central Button
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(TrustBlue)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            onSpeechStart()
                            tryAwaitRelease()
                            isPressed = false
                            onSpeechEnd()
                        }
                    )
                }
        ) {
            // Using a simple text icon placeholder or "Mic" text
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(Color.White))
            }
        }
        
        // Label below
        Text(
            text = if (isPressed) "Mendengarkan..." else "Tekan & Bicara\n(Jual Minyak 1L)",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
