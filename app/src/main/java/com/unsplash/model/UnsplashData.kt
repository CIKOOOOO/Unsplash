package com.unsplash.model

import androidx.annotation.Keep
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Keep
data class UnsplashData(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val url: Url,
    @SerializedName("user") val user: User,
    @SerializedName("alt_description") val description: String = ""
)
@Keep
data class Url(
    @SerializedName("regular") val picture: String
)
@Keep
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_image") val profileImage: ProfileImage
)
@Keep
data class ProfileImage(
    @SerializedName("medium") val medium: String
)