package com.berglund.qapital.storage

import kotlinx.coroutines.flow.Flow

interface LocalStorage<T : Any> {

	fun insert(value: T)

	fun get(key: Int): Flow<StorageResult<T>>

	fun delete(key: Int)
}
