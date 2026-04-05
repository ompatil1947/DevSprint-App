package com.example.robodate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robodate.repository.MatchApiResult
import com.example.robodate.repository.RoboDateRepository
import com.example.robodate.repository.RobotInfoResult
import com.example.robodate.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class RoboDateViewModel : ViewModel() {

    private val repository = RoboDateRepository()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _matchResultState = MutableStateFlow(MatchResultState())
    val matchResultState: StateFlow<MatchResultState> = _matchResultState.asStateFlow()

    init {
        loadNextRobot()
    }

    fun loadNextRobot() {
        val robotName = Constants.ROBOT_NAMES.random()
        val imageUrl = "${Constants.ROBOHASH_BASE_URL}$robotName?size=${Constants.DEFAULT_ROBOHASH_SIZE}"

        _uiState.value = UiState.Loading
        _matchResultState.value = MatchResultState() // Reset result state

        viewModelScope.launch {
            when (val result = repository.getRobotInfo(robotName)) {
                is RobotInfoResult.Success -> {
                    _uiState.value = UiState.Success(
                        robotName = robotName,
                        age = result.age,
                        joke = result.joke,
                        imageUrl = imageUrl
                    )
                }
                is RobotInfoResult.Error -> {
                    _uiState.value = UiState.Error("Failed to fetch robot data: ${result.message}")
                }
            }
        }
    }

    fun onAction(isLike: Boolean) {
        // If not successfully loaded, do nothing
        if (_uiState.value !is UiState.Success) return

        _matchResultState.value = MatchResultState(isLoading = true, showResult = true)

        viewModelScope.launch {
            when (val result = repository.getMatchResult()) {
                is MatchApiResult.Success -> {
                    _matchResultState.value = MatchResultState(
                        showResult = true,
                        answer = result.answer,
                        gifUrl = result.imageUrl,
                        isLoading = false,
                        isError = false
                    )
                }
                is MatchApiResult.Error -> {
                    _matchResultState.value = MatchResultState(
                        showResult = true,
                        answer = "ERROR",
                        gifUrl = "",
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }
}
