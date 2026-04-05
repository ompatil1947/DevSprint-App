package com.example.robodate.ui.screens

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.robodate.ui.theme.AccentColor
import com.example.robodate.ui.theme.AccentSecondary
import com.example.robodate.ui.theme.ActionLike
import com.example.robodate.ui.theme.CardBorderColor
import com.example.robodate.ui.theme.CardSurfaceBackground
import com.example.robodate.ui.theme.DarkSpaceBackground
import com.example.robodate.ui.theme.TextPrimary
import com.example.robodate.ui.theme.TextSecondary

@Composable
fun RobotCard(
    name: String,
    age: Int,
    joke: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    // Pulsing glow animation
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        // Outer glow effect
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            AccentColor.copy(alpha = glowAlpha * 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            AccentColor.copy(alpha = 0.5f),
                            AccentSecondary.copy(alpha = 0.3f),
                            CardBorderColor
                        )
                    ),
                    shape = RoundedCornerShape(28.dp)
                ),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurfaceBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar with gradient ring
                Box(contentAlignment = Alignment.Center) {
                    // Gradient ring behind avatar
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.sweepGradient(
                                    colors = listOf(
                                        AccentColor,
                                        AccentSecondary,
                                        ActionLike,
                                        AccentColor
                                    )
                                )
                            )
                    )
                    // Actual robot image from Robohash
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Robot Avatar for $name",
                        modifier = Modifier
                            .size(210.dp)
                            .clip(CircleShape)
                            .background(DarkSpaceBackground),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Robot name
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Age badge pill
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = AccentColor.copy(alpha = 0.15f),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Age $age  •  Robot",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = AccentColor,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Subtle horizontal divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    CardBorderColor,
                                    Color.Transparent
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Pickup line label
                Text(
                    text = "✨ PICKUP LINE",
                    style = MaterialTheme.typography.labelLarge,
                    color = AccentSecondary,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Dad joke text
                Text(
                    text = "\"$joke\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    lineHeight = 26.sp
                )
            }
        }
    }
}
