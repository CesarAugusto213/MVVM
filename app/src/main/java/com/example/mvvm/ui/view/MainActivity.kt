package com.example.mvvm.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.ui.viewmodel.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quoteViewModel by viewModels<QuoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quoteViewModel.onCreate()

        with(binding) {
            quoteViewModel.quote.observe(this@MainActivity, Observer {
                tvQuote.text = it.quote
                tvAuthor.text = it.author
            })

            quoteViewModel.loading.observe(this@MainActivity, Observer {
                progress.isVisible = it
            })

            viewContainer.setOnClickListener {
                quoteViewModel.randomQuote()
            }
        }
    }
}