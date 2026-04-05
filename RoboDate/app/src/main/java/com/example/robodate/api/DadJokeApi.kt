package com.example.robodate.api

import retrofit2.http.GET
import retrofit2.http.Headers
import com.example.robodate.model.DadJokeResponse

interface DadJokeApi {
    @Headers("Accept: application/json")
    @GET("/")
    suspend fun getJoke(): DadJokeResponse
}
