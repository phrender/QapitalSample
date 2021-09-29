package com.berglund.qapital.database.converters

import androidx.room.TypeConverter
import com.berglund.qapital.models.ActivitiesModel
import com.google.gson.Gson

class ActivityConverter {

	@TypeConverter
	fun toActivity(json: String): ActivitiesModel = Gson().fromJson(json, ActivitiesModel::class.java)

	@TypeConverter
	fun fromActivity(activity: ActivitiesModel): String = Gson().toJson(activity, ActivitiesModel::class.java)
}
