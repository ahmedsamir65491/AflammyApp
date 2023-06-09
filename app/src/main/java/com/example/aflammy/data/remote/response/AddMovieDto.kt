package com.example.aflammy.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddMovieDto(
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null
)