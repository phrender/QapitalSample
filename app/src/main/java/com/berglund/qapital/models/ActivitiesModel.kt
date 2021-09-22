package com.berglund.qapital.models

import java.util.*

data class ActivitiesModel(
    val oldest: Date,
    val activities: List<ActivityModel>
)
