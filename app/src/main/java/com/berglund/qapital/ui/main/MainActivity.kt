package com.berglund.qapital.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.berglund.qapital.contracts.MainContract
import com.berglund.qapital.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onViewCreated()
    }

    override fun welcomeMessage(welcomeMessage: String) {
        binding.tvMainHello.text = welcomeMessage
    }
}