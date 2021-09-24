package com.berglund.qapital.models

import org.threeten.bp.LocalDateTime

data class FeedModel(
    val oldestEntry: LocalDateTime,
    val feedEntries: List<FeedEntryModel>
)
