package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.extensions.apiCall
import com.berglund.qapital.mapper.ActivitiesMapper
import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.network.QapitalApi
import com.berglund.qapital.storage.ActivitiesStorage
import com.berglund.qapital.storage.LocalStorage
import com.berglund.qapital.storage.RoomStorage
import com.berglund.qapital.storage.Storage
import com.berglund.qapital.util.Result
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ActivitiesRepository @Inject constructor(
	private val api: QapitalApi,
	storage: RoomStorage<ActivitiesModel, Storage<ActivitiesModel>>
) : Repository<ActivitiesModel, ActivitiesRepository.RetrievalParams>(storage) {

	@WorkerThread
	override fun fetch(params: RetrievalParams): Result<ActivitiesModel> =
		apiCall(ActivitiesMapper()) { api.getActivities(params.requestFromDate, params.requestToDate) }

	open class RetrievalParams(
		open val requestFromDate: String,
		open val requestToDate: String
	) : Repository.RepositoryParams()
}
