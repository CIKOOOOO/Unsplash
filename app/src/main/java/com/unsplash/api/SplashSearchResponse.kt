package com.unsplash.api

import com.google.gson.annotations.SerializedName
import com.unsplash.model.UnsplashData

data class SplashSearchResponse(
    @SerializedName("total") val total: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("results") val resultList: List<UnsplashData>
)