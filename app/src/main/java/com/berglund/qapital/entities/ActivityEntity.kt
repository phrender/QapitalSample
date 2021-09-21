package com.berglund.qapital.entities

import com.squareup.moshi.Json
import java.util.*

data class ActivityEntity(
    @field:Json(name = "message") val message: String,
    @field:Json(name = "amount") val amount: Double,
    @field:Json(name = "userId") val userId: Int,
    @field:Json(name = "timestamp") val timestamp: Date
)
