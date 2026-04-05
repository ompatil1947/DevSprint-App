package com.example.robodate.model

import com.google.gson.annotations.SerializedName

data class YesNoResponse(
    @SerializedName("answer") val answer: String,
    @SerializedName("forced") val forced: Boolean,
    @SerializedName("image") val image: String
)
