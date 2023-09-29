package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.domain.GetQuoteUseCase
import com.example.mvvm.domain.GetRandomQuoteUseCase
import com.example.mvvm.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    val quote = MutableLiveData<Quote>()
    val loading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            loading.postValue(true)
            val result = getQuoteUseCase()

            if (result.isNotEmpty()) {
                quote.postValue(result[0])
                loading.postValue(false)
            }
        }
    }

    fun randomQuote() {
        viewModelScope.launch {
            loading.postValue(true)
            val quoteRandom = getRandomQuoteUseCase()
            quoteRandom?.let { quote.postValue(it) }
            loading.postValue(false)
        }
    }

}