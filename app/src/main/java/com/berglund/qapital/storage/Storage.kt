package com.berglund.qapital.storage

import kotlinx.coroutines.flow.Flow

interface Storage<DataModel : Any> {

	fun insert(value: DataModel, info: StorageInfo)

	fun get(key: Int): Flow<StorageResult<DataModel>>

	fun delete(key: Int)

	fun currentTime(): Long = System.currentTimeMillis()
}
