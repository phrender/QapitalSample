package com.berglund.qapital.usecase

import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.repository.ActivitiesRepository
import com.berglund.qapital.util.Result
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
class ActivitiesUseCase @Inject constructor(
	private val activitiesRepository: ActivitiesRepository
) : ArgUseCase<Flow<Result<ActivitiesModel>>, ActivitiesUseCase.RetrievalParams> {

	override fun perform(arg: RetrievalParams): Flow<Result<ActivitiesModel>> =
		flowOf(activitiesRepository.fetch(ActivitiesRepository.RetrievalParams(arg.requestFromDate, arg.requestToDate)))

	data class RetrievalParams(
		val requestFromDate: String,
		val requestToDate: String
	)
}
