package com.berglund.qapital.usecase

import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.repository.ActivitiesRepository
import com.berglund.qapital.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ActivitiesUseCase @Inject constructor(
    private val activitiesRepository: ActivitiesRepository
) : ArgUseCase<Flow<Result<ActivitiesModel>>, ActivitiesUseCase.RetrievalParams> {
    override fun perform(arg: RetrievalParams): Flow<Result<ActivitiesModel>> {
        return emptyFlow()
    }
    
    /*
    suspend fun perform(from: String, to: String) = flow {
        val result = activitiesRepository.fetchActivities(from, to)
        emit(result)
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
    */

    data class RetrievalParams(
        val requestFromDate: String,
        val requestToDate: String
    )
}