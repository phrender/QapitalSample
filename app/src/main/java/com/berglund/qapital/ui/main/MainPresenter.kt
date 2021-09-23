package com.berglund.qapital.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berglund.qapital.usecase.ActivitiesUseCase
import com.berglund.qapital.contracts.MainContract
import com.berglund.qapital.models.FeedModel
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.usecase.UserUseCase
import com.berglund.qapital.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val activitiesUseCase: ActivitiesUseCase,
    private val userUseCase: UserUseCase
) : MainContract.Presenter, ViewModel() {

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch(Dispatchers.IO) {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'00:00:00+00:00")
            val calendar = Calendar.getInstance()

            // Set start of september
            calendar.set(2021, 8, 1)

            activitiesUseCase.perform(formatter.format(calendar.time), formatter.format(Date())).collect { result ->
                when (result) {
                    is Result.Success -> {

                        val activities = result.value.activities
                        val userIds = activities.map { activity -> activity.userId }.distinct()

                        val users = ArrayList<UserModel>()
                        userIds.forEach { id ->
                            userUseCase.perform(id).collect { user ->
                                when (user) {
                                    is Result.Success -> {
                                        users.add(user.value)
                                    }
                                    is Result.Error -> {
                                        Timber.e("Failed to fetch data for $id")
                                    }
                                }
                            }
                        }

                        val feedList = activities.map { activity ->
                            FeedModel(
                                activity.message,
                                activity.amount,
                                activity.timestamp,
                                users.find { user -> user.userId == activity.userId }?.avatarUrl ?: ""
                            )
                        }

                        updateActivityList(feedList)
                    }
                    is Result.Error -> {
                        Timber.e("Failed to fetch data!")
                    }
                }
            }
        }
    }

    private fun updateActivityList(feed: List<FeedModel>) {
        viewModelScope.launch(Dispatchers.Main) { view.updateFeedList(feed) }
    }

    override fun onViewCreated() {
    }
}