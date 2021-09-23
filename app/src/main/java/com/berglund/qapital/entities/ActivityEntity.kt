package com.berglund.qapital.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class ActivityEntity(
    @SerializedName("message")
    val message: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("timestamp")
    val timestamp: Date
)