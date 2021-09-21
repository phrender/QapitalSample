package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.netwotk.QapitalApi
import java.util.*
import javax.inject.Inject

class ActivitiesRepository @Inject constructor(
    private val api: QapitalApi
) : Repository {

    @WorkerThread
    fun fetchActivities(from: Date, to: Date) {
    }
}