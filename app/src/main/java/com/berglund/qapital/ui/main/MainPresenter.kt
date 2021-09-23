package com.berglund.qapital.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berglund.qapital.adapters.PaginationScrollListener
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
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val activitiesUseCase: ActivitiesUseCase,
    private val userUseCase: UserUseCase
) : MainContract.Presenter, ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00+00:00")
    private var requestFromDate: LocalDateTime = LocalDateTime.now()
    private var requestToDate: LocalDateTime = LocalDateTime.now().minusWeeks(2)

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch(Dispatchers.IO) {
            activitiesUseCase.perform(dateFormatter.format(requestFromDate), dateFormatter.format(requestToDate)).collect { result ->
                when (result) {
                    is Result.Success -> {

                        val activities = result.value.activities

                        if (activities.isEmpty()) {
                            requestFromDate = result.value.oldest
                            requestToDate = result.value.oldest.plusWeeks(2)
                            loadActivities()
                        }

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

                        requestFromDate = requestToDate
                        requestToDate = requestFromDate.minusWeeks(2)
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

    override fun getScrollListener(linearLayoutManager: LinearLayoutManager) = object : PaginationScrollListener(linearLayoutManager) {
        override fun loadMoreItems() {
            loadActivities()
        }

        override fun isLoading(): Boolean {
            TODO("Not yet implemented")
        }
    }
}