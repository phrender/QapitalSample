package com.berglund.qapital.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.berglund.qapital.database.converters.ActivityConverter
import com.berglund.qapital.database.converters.StorageInfoConverter
import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.storage.StorageInfo

@Entity(tableName = "activities")
data class ActivitiesDbEntity(
	@TypeConverters(ActivityConverter::class)
	val activities: ActivitiesModel,
	@TypeConverters(StorageInfoConverter::class)
	val metadata: StorageInfo
) {
	@PrimaryKey(autoGenerate = false)
	var id: Int = 0
}
