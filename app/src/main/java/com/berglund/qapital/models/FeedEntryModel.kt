package com.berglund.qapital.models

import org.threeten.bp.LocalDateTime

data class FeedEntryModel(
	val message: String,
	val amount: Double,
	val timestamp: LocalDateTime,
	val avatarUrl: String
)
