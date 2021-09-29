package com.berglund.qapital.repository

import com.berglund.qapital.storage.LocalStorage
import com.berglund.qapital.storage.Storage
import com.berglund.qapital.storage.StorageInfo
import com.berglund.qapital.storage.StorageResult
import com.berglund.qapital.util.Result
import com.berglund.qapital.util.toShortString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import timber.log.Timber

abstract class Repository<Model : Any, in Params : Repository.RepositoryParams>(
	private val storage: LocalStorage<Model>
) {

	companion object {
		private const val NO_MAX_AGE = -1L
	}

	abstract fun fetch(params: Params): Result<Model>

	open fun get(params: Params): Flow<Result<Model>> = flow {
		val localResult = storage.get(params.key).first()
		Timber.d("Validating local data: ${localResult.toResult().toShortString()} with params: $params")

		when {
			localResult is StorageResult.Error -> {
				val networkResult = refreshFromNetwork(params)

				if (networkResult is Result.Error) {
					emit(networkResult)
				}

				storage.get(params.key).dropWhile {
					localResult == it
				}.collect {
					emit(it.toResult())
				}
			}

			localResult is StorageResult.Success && shouldRefreshData(localResult, params) -> {
				refreshFromNetwork(params)

				storage.get(params.key).collect { emit(it.toResult()) }
			}

			else -> storage.get(params.key).collect { emit(it.toResult()) }
		}
	}

	private fun refreshFromNetwork(params: Params): Result<Model> =
		when (val result = fetch(params)) {
			is Result.Success -> {
				storage.insert(result.value)
				result
			}
			is Result.Error -> {
				Timber.d("Failed to refresh data from network!")
				result
			}
		}

	// TODO: Remove me
	@Suppress("UNUSED_PARAMETER")
	private fun shouldRefreshData(result: StorageResult<Model>, params: Params) =
		params.refreshAtAge != NO_MAX_AGE // Add a time limit in other params

	open class RepositoryParams(
		open val refreshAtAge: Long = NO_MAX_AGE,
		open val key: Int = 0
	)
}
