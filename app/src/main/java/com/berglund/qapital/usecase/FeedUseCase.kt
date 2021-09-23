package com.berglund.qapital.usecase

import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.models.FeedModel
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.util.Result
import com.berglund.qapital.util.into
import com.berglund.qapital.util.intoFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FeedUseCase @Inject constructor(
    private val activitiesUseCase: ActivitiesUseCase,
    private val userUseCase: UserUseCase
) : ArgUseCase<Flow<Result<FeedModel>>, FeedUseCase.RetrievalParams> {

    override fun perform(arg: RetrievalParams): Flow<Result<FeedModel>> {
        val activities = activitiesUseCase.perform(ActivitiesUseCase.RetrievalParams(arg.requestFromDate, arg.requestToDate))
        val userIds = activities.map { getUserIds(it) }



        return emptyFlow()
    }

    private fun getUserIds(activitiesResult: Result<ActivitiesModel>): Result<List<Int>> = activitiesResult into {
        Result.Success(it.activities.map { activity -> activity.userId }.distinct())
    }

    sealed class RetrievalParams(
        open val requestFromDate: String,
        open val requestToDate: String
    )
}