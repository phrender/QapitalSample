package com.berglund.qapital.usecase

import com.berglund.qapital.models.UserModel
import com.berglund.qapital.repository.UserRepository
import com.berglund.qapital.util.Result
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class UserUseCase @Inject constructor(
	private val userRepository: UserRepository
) : ArgUseCase<Flow<Result<UserModel>>, UserUseCase.RetrievalParams> {

	override fun perform(arg: RetrievalParams): Flow<Result<UserModel>> =
		userRepository.get(UserRepository.RetrievalParams(arg.userId))
			.distinctUntilChanged()
			.flowOn(Dispatchers.IO)

	data class RetrievalParams(
		val userId: Int
	)
}
