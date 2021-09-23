package com.berglund.qapital.models

data class FeedModel(
    val message: String,
    val amount: Double,
    val timestamp: String,
    val avatarUrl: String
)
