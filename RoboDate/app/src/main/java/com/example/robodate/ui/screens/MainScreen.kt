package com.example.robodate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.robodate.ui.theme.AccentColor
import com.example.robodate.ui.theme.DarkSpaceBackground
import com.example.robodate.ui.theme.MatchNo
import com.example.robodate.viewmodel.RoboDateViewModel
import com.example.robodate.viewmodel.UiState

@Composable
fun MainScreen(viewModel: RoboDateViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val matchState by viewModel.matchResultState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkSpaceBackground)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(color = AccentColor)
                    Text(
                        text = "Finding your robot match...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            is UiState.Error -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "⚠️",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = state.message,
                        color = MatchNo,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Button(
                        onClick = { viewModel.loadNextRobot() },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentColor)
                    ) {
                        Text("Retry", fontWeight = FontWeight.Bold)
                    }
                }
            }

            is UiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // App title bar
                    Text(
                        text = "❤️ RoboDate",
                        style = MaterialTheme.typography.titleLarge,
                        color = AccentColor,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                    )

                    RobotCard(
                        name = state.robotName,
                        age = state.age,
                        joke = state.joke,
                        imageUrl = state.imageUrl,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    ActionButtons(
                        onPass = { viewModel.onAction(isLike = false) },
                        onLike = { viewModel.onAction(isLike = true) }
                    )
                }
            }
        }

        // Match Result Popup overlays everything when active
        MatchResultPopup(
            state = matchState,
            onNextRobot = { viewModel.loadNextRobot() }
        )
    }
}
