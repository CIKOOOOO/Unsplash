package com.unsplash.api

data class LoginResponse(
    val status: String,
    val message: String,
    val token: String
)