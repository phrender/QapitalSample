package com.berglund.qapital.usecase

import com.berglund.qapital.models.UserModel
import com.berglund.qapital.repository.UserRepository
import com.berglund.qapital.util.Result
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
class UserUseCase @Inject constructor(
	private val userRepository: UserRepository
) : ArgUseCase<Flow<Result<UserModel>>, UserUseCase.RetrievalParams> {

	override fun perform(arg: RetrievalParams): Flow<Result<UserModel>> =
		flowOf(userRepository.fetch(UserRepository.RetrievalParams(arg.userId)))

	data class RetrievalParams(
		val userId: Int
	)
}
