package com.example.robodate.repository

import com.example.robodate.api.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class RoboDateRepository {

    suspend fun getRobotInfo(name: String): RobotInfoResult = coroutineScope {
        try {
            Timber.d("Fetching robot info for $name")
            val ageDeferred = async { RetrofitClient.agifyApi.getAge(name) }
            val jokeDeferred = async { RetrofitClient.dadJokeApi.getJoke() }

            val ageResponse = ageDeferred.await()
            val jokeResponse = jokeDeferred.await()

            Timber.d("Successfully fetched info for $name")
            RobotInfoResult.Success(
                age = ageResponse.age ?: 0,
                joke = jokeResponse.joke
            )
        } catch (e: Exception) {
            Timber.e(e, "Error fetching robot info")
            RobotInfoResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getMatchResult(): MatchApiResult {
        return try {
            Timber.d("Fetching match result")
            val response = RetrofitClient.yesNoApi.getMatchResult()
            MatchApiResult.Success(
                answer = response.answer.uppercase(),
                imageUrl = response.image
            )
        } catch (e: Exception) {
            Timber.e(e, "Error fetching match result")
            MatchApiResult.Error(e.message ?: "Could not fetch match result")
        }
    }
}

sealed class RobotInfoResult {
    data class Success(val age: Int, val joke: String) : RobotInfoResult()
    data class Error(val message: String) : RobotInfoResult()
}

sealed class MatchApiResult {
    data class Success(val answer: String, val imageUrl: String) : MatchApiResult()
    data class Error(val message: String) : MatchApiResult()
}
