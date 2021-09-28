package com.berglund.qapital.storage

import com.berglund.qapital.database.dao.UserDao
import com.berglund.qapital.database.entities.UserDbEntity
import com.berglund.qapital.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserStorage @Inject constructor(
	private val dao: UserDao
) : Storage<UserModel> {

	override fun insert(value: UserModel, info: StorageInfo) {
		dao.insert(UserDbEntity(value.userId, value, info))
	}

	override fun get(key: Int): Flow<StorageResult<UserModel>> = flow {
		dao.get(key).collect { list ->
			val result = list.firstOrNull()?.let {
				StorageResult.Success(it.user, it.metadata)
			} ?: StorageResult.Error("Failed to fetch user data for id: $key", null, StorageInfo(currentTime()))

			emit(result)
		}
	}

	override fun delete(key: Int) {
		dao.delete(key)
	}
}
