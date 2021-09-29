package com.berglund.qapital.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class RoomStorage<Model : Any, Dao : Storage<Model>>(
	private val storage: Dao
) : LocalStorage<Model> {

	override fun insert(value: Model) {
		storage.insert(value, StorageInfo(currentTime()))
	}

	override fun get(key: Int): Flow<StorageResult<Model>> = flow {
		storage.get(key).collect {
			val result = when (it) {
				is StorageResult.Success -> StorageResult.Success(it.value, it.info)
				is StorageResult.Error -> it.copy()
			}

			emit(result)
		}
	}

	override fun delete(key: Int) {
		storage.delete(key)
	}

	override fun toString(): String {
		return "RoomStorage(storage: ${storage.javaClass.simpleName})"
	}

	private fun currentTime() = System.currentTimeMillis()
}
