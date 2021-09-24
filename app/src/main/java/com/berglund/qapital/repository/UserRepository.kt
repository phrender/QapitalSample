package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.extensions.apiCall
import com.berglund.qapital.mapper.UserMapper
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.network.QapitalApi
import com.berglund.qapital.util.Result
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: QapitalApi
) : Repository<UserModel, UserRepository.RetrievalParams>() {

    @WorkerThread
    override fun fetch(params: RetrievalParams): Result<UserModel> =
        apiCall(UserMapper()) { api.getUser(params.userId) }

    open class RetrievalParams(
        open val userId: Int
    ) : Repository.RepositoryParams()
}