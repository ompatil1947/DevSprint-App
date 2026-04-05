package com.example.robodate.ui.screens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.robodate.ui.theme.AccentColor
import com.example.robodate.ui.theme.DarkSpaceBackground
import com.example.robodate.ui.theme.MatchNo
import com.example.robodate.ui.theme.MatchYes
import com.example.robodate.viewmodel.MatchResultState

@Composable
fun MatchResultPopup(
    state: MatchResultState,
    onNextRobot: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Use remember to avoid rebuilding the ImageLoader on every recomposition
    val imageLoader = remember(context) {
        ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    AnimatedVisibility(
        visible = state.showResult,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSpaceBackground.copy(alpha = 0.97f)),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = AccentColor)
            } else {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (state.isError) {
                        Text(
                            text = "⚠️ Could not evaluate match",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MatchNo,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Something went wrong. Try the next robot!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        // YES / NO answer with colour coding
                        val answerColor = if (state.answer == "YES") MatchYes else MatchNo
                        val answerEmoji = if (state.answer == "YES") "💚 It's a Match!" else "💔 No Match"

                        Text(
                            text = answerEmoji,
                            style = MaterialTheme.typography.headlineLarge,
                            color = answerColor,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = state.answer,
                            style = MaterialTheme.typography.displayLarge,
                            color = answerColor,
                            fontWeight = FontWeight.Black
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        if (state.gifUrl.isNotEmpty()) {
                            AsyncImage(
                                model = state.gifUrl,
                                contentDescription = "Reaction GIF",
                                imageLoader = imageLoader,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = onNextRobot,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentColor,
                            contentColor = DarkSpaceBackground
                        )
                    ) {
                        Text(
                            text = "Next Robot →",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
