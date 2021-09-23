package com.berglund.qapital.mapper

import com.berglund.qapital.entities.ActivitiesEntity
import com.berglund.qapital.models.ActivitiesModel
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class ActivitiesMapper: Mapper<ActivitiesEntity, ActivitiesModel> {

    private val activityMapper = ActivityMapper()

    override fun map(model: ActivitiesEntity): ActivitiesModel = model.toModel()

    private fun ActivitiesEntity.toModel(): ActivitiesModel = ActivitiesModel(
        LocalDateTime.ofInstant(Instant.ofEpochMilli(oldest.time), ZoneId.systemDefault()),
        activities?.map {
            activityMapper.map(it)
        } ?: emptyList()
    )
}