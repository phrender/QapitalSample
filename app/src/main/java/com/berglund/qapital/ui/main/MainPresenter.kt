package com.berglund.qapital.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.berglund.qapital.adapters.NextPageLoaderListener
import com.berglund.qapital.adapters.PaginationScrollListener
import com.berglund.qapital.contracts.MainContract
import com.berglund.qapital.models.FeedEntryModel
import com.berglund.qapital.usecase.FeedUseCase
import com.berglund.qapital.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val feedUseCase: FeedUseCase
) : MainContract.Presenter, ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00+00:00")
    private var requestFromDate: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault()).minusWeeks(2)
    private var requestToDate: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault())

    override fun loadFeed() {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch(Dispatchers.IO) {
            feedUseCase.perform(FeedUseCase.RetrievalParams(dateFormatter.format(requestFromDate), dateFormatter.format(requestToDate))).collect { feed ->
                when (feed) {
                    is Result.Success -> {
                        if (feed.value.feedEntries.isEmpty()) {
                            requestFromDate = requestFromDate.minusWeeks(2)
                            loadActivities()
                        }

                        updateActivityList(feed.value.feedEntries)

                        requestFromDate = requestFromDate.minusWeeks(2)
                    }
                    is Result.Error -> {
                        viewModelScope.launch { view.isLoadingData(false) }
                        Timber.e("Failed to load feed data!")
                    }
                }
            }
        }
    }

    private fun updateActivityList(feed: List<FeedEntryModel>) {
        viewModelScope.launch { view.updateFeedList(feed) }
    }

    override fun getScrollListener() = object : NextPageLoaderListener {
        override fun loadNextPage() {
            loadActivities()
        }
    }
}