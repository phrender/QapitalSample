package com.berglund.qapital.storage

import com.berglund.qapital.util.Result
import java.lang.Exception

sealed class StorageResult<out T : Any>(open val info: StorageInfo) {
	data class Success<out T : Any>(val value: T, override val info: StorageInfo) : StorageResult<T>(info)
	data class Error(val message: String, val cause: Exception? = null, override val info: StorageInfo) : StorageResult<Nothing>(info)

	fun toResult(): Result<T> = when (this) {
		is Success -> Result.Success(value)
		is Error -> Result.Error(message, cause)
	}
}

data class StorageInfo(
	val lastUpdated: Long
)
