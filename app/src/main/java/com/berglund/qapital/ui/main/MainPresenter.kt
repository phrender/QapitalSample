package com.berglund.qapital.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berglund.qapital.contracts.MainContract
import com.berglund.qapital.models.ActivityModel
import com.berglund.qapital.repository.ActivitiesRepository
import com.berglund.qapital.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val repository: ActivitiesRepository
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

            val repoResult = repository.fetchActivities(formatter.format(calendar.time), formatter.format(Date()))
            when (repoResult) {
                is Result.Success -> {
                    repoResult.value.let {
                        updateActivityList(it.activities)
                    }
                }
                is Result.Error -> {
                    Timber.e("Failed to fetch data!")
                }
            }
        }
    }

    private fun updateActivityList(activities: List<ActivityModel>) {
        viewModelScope.launch(Dispatchers.Main) { view.updateActivityList(activities) }
    }

    override fun onViewCreated() {
    }
}