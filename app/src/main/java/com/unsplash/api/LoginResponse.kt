package com.unsplash.api

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    val status: String,
    val message: String,
    val token: String
)