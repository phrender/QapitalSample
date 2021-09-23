package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.extensions.apiCall
import com.berglund.qapital.mapper.UserMapper
import com.berglund.qapital.network.QapitalApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: QapitalApi
) : Repository {

    @WorkerThread
    fun fetchUser(userId: Int) = apiCall(UserMapper()) {
        api.getUser(userId)
    }
}