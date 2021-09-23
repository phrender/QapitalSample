package com.berglund.qapital.usecase

import com.berglund.qapital.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun perform(userId: Int) = flow {
        val result = userRepository.fetchUser(userId)
        emit(result)
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
}