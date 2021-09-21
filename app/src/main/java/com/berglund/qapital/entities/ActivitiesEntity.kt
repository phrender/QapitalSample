package com.berglund.qapital.entities

import com.squareup.moshi.Json
import java.util.*

data class ActivitiesEntity(
    @field:Json(name = "oldest") val oldest: Date,
    @field:Json(name = "activities") val activities: List<ActivityEntity>
)
