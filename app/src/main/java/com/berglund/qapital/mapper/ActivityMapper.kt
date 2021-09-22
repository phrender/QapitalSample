package com.berglund.qapital.mapper

import com.berglund.qapital.entities.ActivityEntity
import com.berglund.qapital.models.ActivityModel

class ActivityMapper : Mapper<ActivityEntity, ActivityModel> {

    override fun map(entity: ActivityEntity): ActivityModel = entity.toModel()

    private fun ActivityEntity.toModel(): ActivityModel = ActivityModel(
        message,
        amount,
        userId,
        timestamp
    )
}
