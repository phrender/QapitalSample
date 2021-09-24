package com.berglund.qapital.usecase

import com.berglund.qapital.extensions.combineResults
import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.models.FeedEntryModel
import com.berglund.qapital.models.FeedModel
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.repository.UserRepository
import com.berglund.qapital.util.Result
import com.berglund.qapital.util.into
import com.berglund.qapital.util.intoFlow
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

@FlowPreview
@ExperimentalCoroutinesApi
class FeedUseCase @Inject constructor(
	private val activitiesUseCase: ActivitiesUseCase,
	private val userRepository: UserRepository
) : ArgUseCase<Flow<Result<FeedModel>>, FeedUseCase.RetrievalParams> {

	override fun perform(arg: RetrievalParams): Flow<Result<FeedModel>> {
		val activities = activitiesUseCase.perform(ActivitiesUseCase.RetrievalParams(arg.requestFromDate, arg.requestToDate))
		val users = activities.flatMapMerge { activitiesResult ->
			val userIds = getUserIds(activitiesResult)
			getUserData(userIds)
		}

		return combineResults(activities, users) { activitiesResult, usersResult ->
			// Merge the activities and userdata
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
			.distinctUntilChanged()
			.flowOn(Dispatchers.IO)
			.debounce(250L)
	}

	private fun getUserIds(activitiesResult: Result<ActivitiesModel>): Result<List<Int>> = activitiesResult into {
		Result.Success(it.activities.map { activity -> activity.userId }.distinct())
	}

	private fun getUserData(userIds: Result<List<Int>>): Flow<Result<List<UserModel>>> = userIds intoFlow { ids ->
		val users = ArrayList<UserModel>()
		ids.forEach { id ->
			when (val result = userRepository.fetch(UserRepository.RetrievalParams(id))) {
				is Result.Success -> { users.add(result.value) }
				is Result.Error -> Result.Error("Failed to fetch user data!")
			}
		}
		flowOf(Result.Success(users))
	}

	data class RetrievalParams(
		val requestFromDate: String,
		val requestToDate: String
	)
}
