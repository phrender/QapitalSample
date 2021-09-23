package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.extensions.apiCall
import com.berglund.qapital.mapper.ActivitiesMapper
import com.berglund.qapital.mapper.ActivityMapper
import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.network.QapitalApi
import com.berglund.qapital.usecase.ActivitiesUseCase
import com.berglund.qapital.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ActivitiesRepository @Inject constructor(
    private val api: QapitalApi
) : Repository<ActivitiesModel, ActivitiesRepository.RetrievalParams>() {

    @WorkerThread
    override fun fetch(params: RetrievalParams): Result<ActivitiesModel> =
        apiCall(ActivitiesMapper()) { api.getActivities(params.requestFromDate, params.requestToDate) }

    sealed class RetrievalParams(
        open val requestFromDate: String,
        open val requestToDate: String
    ) : Repository.RepositoryParams()
}