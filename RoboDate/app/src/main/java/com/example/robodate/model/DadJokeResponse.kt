package com.example.robodate.model

import com.google.gson.annotations.SerializedName

data class DadJokeResponse(
    @SerializedName("id") val id: String,
    @SerializedName("joke") val joke: String,
    @SerializedName("status") val status: Int
)
