package com.example.robodate.api

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.robodate.model.AgifyResponse

interface AgifyApi {
    @GET("/")
    suspend fun getAge(@Query("name") name: String): AgifyResponse
}
