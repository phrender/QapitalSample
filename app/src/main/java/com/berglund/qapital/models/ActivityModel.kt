package com.berglund.qapital.models

import org.threeten.bp.LocalDateTime

data class ActivityModel(
    val message: String,
    val amount: Double,
    val userId: Int,
    val timestamp: LocalDateTime
)
