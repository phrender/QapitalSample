package com.berglund.qapital.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.berglund.qapital.adapters.FeedAdapter
import com.berglund.qapital.contracts.MainContract
import com.berglund.qapital.databinding.ActivityMainBinding
import com.berglund.qapital.models.FeedModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter
    private lateinit var binding: ActivityMainBinding
    private val adapter = FeedAdapter(emptyList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onViewCreated()
        binding.rvMainActivityList.adapter = adapter
    }

    override fun updateFeedList(feed: List<FeedModel>) {
        adapter.updateFeedList(feed)
    }
}