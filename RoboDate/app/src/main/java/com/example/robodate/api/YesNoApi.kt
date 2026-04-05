package com.example.robodate.api

import retrofit2.http.GET
import com.example.robodate.model.YesNoResponse

interface YesNoApi {
    @GET("api")
    suspend fun getMatchResult(): YesNoResponse
}
