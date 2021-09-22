package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.extensions.apiCall
import com.berglund.qapital.mapper.ActivitiesMapper
import com.berglund.qapital.network.QapitalApi
import javax.inject.Inject

class ActivitiesRepository @Inject constructor(
    private val api: QapitalApi
) : Repository {

    private val mapper = ActivitiesMapper()

    @WorkerThread
    suspend fun fetchActivities(from: String, to: String) = apiCall(mapper) {
        api.getActivities(from, to)
    }
}