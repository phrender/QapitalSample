package com.berglund.qapital.mapper

import com.berglund.qapital.entities.ActivityEntity
import com.berglund.qapital.models.ActivityModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class ActivityMapper : Mapper<ActivityEntity, ActivityModel> {

	override fun map(model: ActivityEntity): ActivityModel = model.toModel()

	private fun ActivityEntity.toModel(): ActivityModel = ActivityModel(
		message,
		amount,
		userId,
		LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+00:00"))
	)
}
