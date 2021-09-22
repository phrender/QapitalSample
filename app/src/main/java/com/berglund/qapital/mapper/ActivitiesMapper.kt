package com.berglund.qapital.mapper

import com.berglund.qapital.entities.ActivitiesEntity
import com.berglund.qapital.models.ActivitiesModel

class ActivitiesMapper: Mapper<ActivitiesEntity, ActivitiesModel> {

    private val activityMapper = ActivityMapper()

    override fun map(entity: ActivitiesEntity): ActivitiesModel = entity.toModel()

    private fun ActivitiesEntity.toModel(): ActivitiesModel = ActivitiesModel(
        oldest,
        activities?.map {
            activityMapper.map(it)
        } ?: emptyList()
    )
}