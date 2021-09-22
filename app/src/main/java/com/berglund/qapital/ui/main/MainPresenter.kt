package com.berglund.qapital.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berglund.qapital.contracts.MainContract
import com.berglund.qapital.repository.ActivitiesRepository
import com.berglund.qapital.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                    view.welcomeMessage("Successfully fetch data")
                }
                is Result.Error -> {
                    view.welcomeMessage("Failed to fetch data")
                }
            }
        }
    }

    override fun onViewCreated() {
    }
}