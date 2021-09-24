package com.berglund.qapital.models

import org.threeten.bp.LocalDateTime

data class ActivitiesModel(
	val oldest: LocalDateTime,
	val activities: List<ActivityModel>
)
