package com.berglund.qapital.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserEntity(
    @field:Json(name = "userId") val userId: Int,
    @field:Json(name = "displayName") val displayName: String,
    @field:Json(name = "avatarUrl") val avatarUrl: String
)
