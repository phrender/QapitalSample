package com.berglund.qapital.storage

import com.berglund.qapital.database.dao.ActivityDao
import com.berglund.qapital.database.entities.ActivitiesDbEntity
import com.berglund.qapital.models.ActivitiesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActivitiesStorage @Inject constructor(
	private val dao: ActivityDao
) : Storage<ActivitiesModel> {

	override fun insert(value: ActivitiesModel, info: StorageInfo) {
		dao.insert(ActivitiesDbEntity(value, info))
	}

	override fun get(key: Int): Flow<StorageResult<ActivitiesModel>> = flow {
		dao.get(key).collect { list ->
			val result = list.firstOrNull()?.let {
				StorageResult.Success(it.activities, it.metadata)
			} ?: StorageResult.Error("Failed to retrieve activity data for id: $key", null, StorageInfo(currentTime()))

			emit(result)
		}
	}

	override fun delete(key: Int) {
		dao.delete(key)
	}
}
