package com.berglund.qapital.models

import org.threeten.bp.LocalDateTime
import java.util.*

data class FeedModel(
    val message: String,
    val amount: Double,
    val timestamp: LocalDateTime,
    val avatarUrl: String
)
