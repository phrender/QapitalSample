package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.netwotk.QapitalApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: QapitalApi
) : Repository {

    @WorkerThread
    fun fetchUser(userId: Int) {
    }
}