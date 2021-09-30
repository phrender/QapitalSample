package com.berglund.qapital.usecase

import com.berglund.qapital.extensions.combineResults
import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.models.FeedEntryModel
import com.berglund.qapital.models.FeedModel
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.repository.ActivitiesRepository
import com.berglund.qapital.repository.UserRepository
import com.berglund.qapital.util.Result
import com.berglund.qapital.util.into
import com.berglund.qapital.util.intoFlow
import com.berglund.qapital.util.suspendInto
import javax.inject.Inject
import kotlin.math.atan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@FlowPreview
@ExperimentalCoroutinesApi
class FeedUseCase @Inject constructor(
	private val activitiesRepository: ActivitiesRepository,
	private val userRepository: UserRepository
) : ArgUseCase<Flow<Result<FeedModel>>, FeedUseCase.RetrievalParams> {
	override fun perform(arg: RetrievalParams): Flow<Result<FeedModel>> {
		val activities = activitiesRepository.get(ActivitiesRepository.RetrievalParams(arg.requestFromDate, arg.requestToDate))
		val users = activities.flatMapMerge { getUserIds(it) }
			.map { userIds ->
				userIds suspendInto { ids ->
					getUsersData(ids)
				}
			}

		return combineResults(activities, users) { activitiesResult, usersResult ->
			Result.Success(
				FeedModel(
					activitiesResult.oldest,
					activitiesResult.activities.map { activity ->
						FeedEntryModel(
							activity.message,
							activity.amount,
							activity.timestamp,
							usersResult.find { user -> user.userId == activity.userId }?.avatarUrl ?: ""
						)
					}
				)
			)
		}
			.debounce(250L)
			.distinctUntilChanged()
			.flowOn(Dispatchers.IO)
	}

	private fun getUserIds(activitiesResult: Result<ActivitiesModel>): Flow<Result<List<Int>>> = activitiesResult intoFlow { activities ->
		flowOf(Result.Success(activities.activities.map { activity -> activity.userId }.distinct()))
	}

	private fun getUsersData(userIds: List<Int>): Result<List<UserModel>> {
		val users = ArrayList<UserModel>()
		userIds.map { id ->
			when (val result = userRepository.fetch(UserRepository.RetrievalParams(id))) {
				is Result.Success -> users.add(result.value)
				is Result.Error -> Result.Error(result.message, result.cause)
			}
		}
		return Result.Success(users)
	}

	data class RetrievalParams(
		val requestFromDate: String,
		val requestToDate: String
	)
}
