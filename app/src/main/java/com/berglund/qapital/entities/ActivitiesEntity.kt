package com.berglund.qapital.entities

import com.google.gson.annotations.SerializedName

data class ActivitiesEntity(
	@SerializedName("oldest")
	val oldest: String,
	@SerializedName("activities")
	val activities: List<ActivityEntity>?
)
