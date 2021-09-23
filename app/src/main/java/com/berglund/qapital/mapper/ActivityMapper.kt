package com.berglund.qapital.mapper

import com.berglund.qapital.entities.ActivityEntity
import com.berglund.qapital.models.ActivityModel
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class ActivityMapper : Mapper<ActivityEntity, ActivityModel> {

    override fun map(entity: ActivityEntity): ActivityModel = entity.toModel()

    private fun ActivityEntity.toModel(): ActivityModel = ActivityModel(
        message,
        amount,
        userId,
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.time), ZoneId.systemDefault())
    )
}
