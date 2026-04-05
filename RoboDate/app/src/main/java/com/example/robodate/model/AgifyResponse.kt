package com.example.robodate.model

import com.google.gson.annotations.SerializedName

data class AgifyResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int?
)
