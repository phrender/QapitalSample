package com.berglund.qapital.entities

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("avatarUrl")
    val avatarUrl: String
)
