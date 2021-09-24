package com.berglund.qapital.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.berglund.qapital.adapters.FeedAdapter
import com.berglund.qapital.contracts.MainContract
import com.berglund.qapital.databinding.ActivityMainBinding
import com.berglund.qapital.models.FeedEntryModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter
    private lateinit var binding: ActivityMainBinding
    private val adapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMainActivityList.layoutManager = LinearLayoutManager(baseContext)
        adapter.nextPageListener = presenter.getScrollListener()
        binding.rvMainActivityList.adapter = adapter

        presenter.loadFeed()
    }

    override fun updateFeedList(feed: List<FeedEntryModel>) {
        val lastScrollPosition = (binding.rvMainActivityList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        adapter.updateFeedList(feed)
        (binding.rvMainActivityList.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(lastScrollPosition, 0)
    }

    override fun isLoadingData(isLoading: Boolean) {
        binding.pbMainProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}