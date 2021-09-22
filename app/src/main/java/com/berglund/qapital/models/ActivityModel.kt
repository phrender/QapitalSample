package com.berglund.qapital.models

data class ActivityModel(
    val message: String,
    val amount: Double,
    val userId: Int,
    val timestamp: String
)
