package com.unsplash.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UnsplashData(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val url: Url,
    @SerializedName("user") val user: User,
    @SerializedName("alt_description") val description: String = ""
)

data class Url(
    @SerializedName("regular") val picture: String
)

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_image") val profileImage: ProfileImage
)

data class ProfileImage(
    @SerializedName("medium") val medium: String
)