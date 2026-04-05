package com.example.robodate.viewmodel

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val robotName: String,
        val age: Int,
        val joke: String,
        val imageUrl: String
    ) : UiState()
    data class Error(val message: String) : UiState()
}

data class MatchResultState(
    val showResult: Boolean = false,
    val answer: String = "",
    val gifUrl: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false
)
