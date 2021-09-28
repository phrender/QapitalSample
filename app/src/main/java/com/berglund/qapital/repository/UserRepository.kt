package com.berglund.qapital.repository

import androidx.annotation.WorkerThread
import com.berglund.qapital.extensions.apiCall
import com.berglund.qapital.mapper.UserMapper
import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.network.QapitalApi
import com.berglund.qapital.storage.LocalStorage
import com.berglund.qapital.storage.RoomStorage
import com.berglund.qapital.storage.Storage
import com.berglund.qapital.storage.UserStorage
import com.berglund.qapital.util.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserRepository @Inject constructor(
	private val api: QapitalApi,
	storage: RoomStorage<UserModel, Storage<UserModel>>
) : Repository<UserModel, UserRepository.RetrievalParams>(storage) {

	@WorkerThread
	override fun fetch(params: RetrievalParams): Result<UserModel> =
		apiCall(UserMapper()) { api.getUser(params.userId) }

	open class RetrievalParams(
		open val userId: Int
	) : Repository.RepositoryParams(key = userId)
}
