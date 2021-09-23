package com.berglund.qapital.usecase

import com.berglund.qapital.repository.ActivitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ActivitiesUseCase @Inject constructor(
    private val activitiesRepository: ActivitiesRepository
) {

    suspend fun perform(from: String, to: String) = flow {
        val result = activitiesRepository.fetchActivities(from, to)
        emit(result)
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
}