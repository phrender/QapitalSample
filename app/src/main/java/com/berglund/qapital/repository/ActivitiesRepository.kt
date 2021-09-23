package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.extensions.apiCall
import com.berglund.qapital.mapper.ActivitiesMapper
import com.berglund.qapital.network.QapitalApi
import javax.inject.Inject

class ActivitiesRepository @Inject constructor(
    private val api: QapitalApi
) : Repository {

    @WorkerThread
    fun fetchActivities(from: String, to: String) = apiCall(ActivitiesMapper()) {
        api.getActivities(from, to)
    }
}