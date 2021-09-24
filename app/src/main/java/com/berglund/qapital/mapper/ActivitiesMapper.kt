package com.berglund.qapital.mapper

import com.berglund.qapital.entities.ActivitiesEntity
import com.berglund.qapital.models.ActivitiesModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class ActivitiesMapper : Mapper<ActivitiesEntity, ActivitiesModel> {

	private val activityMapper = ActivityMapper()

	override fun map(model: ActivitiesEntity): ActivitiesModel = model.toModel()

	private fun ActivitiesEntity.toModel(): ActivitiesModel = ActivitiesModel(
		LocalDateTime.parse(oldest, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+00:00")),
		activities?.map {
			activityMapper.map(it)
		} ?: emptyList()
	)
}
