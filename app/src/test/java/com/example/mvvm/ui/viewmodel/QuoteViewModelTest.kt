package com.example.mvvm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvm.domain.GetQuoteUseCase
import com.example.mvvm.domain.GetRandomQuoteUseCase
import com.example.mvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuoteViewModelTest {

    @RelaxedMockK
    private lateinit var getQuoteUseCase: GetQuoteUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    private lateinit var quoteViewModel: QuoteViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(getQuoteUseCase, getRandomQuoteUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all quotes and set the fist value`() =
        runTest {
            //Given
            val quoteList = listOf(Quote("HOLA", "CESAR"), Quote("HOLA2", "CESAR2"))
            coEvery { getQuoteUseCase() } returns quoteList
            //When
            quoteViewModel.onCreate()
            //Then
            assert(quoteViewModel.quote.value == quoteList.first())
        }

    @Test
    fun `when randomQuoteUseCase return a quote set on the livedata`() = runTest {
        //Given
        val quote = Quote("HOLA", "CESAR")
        coEvery { getRandomQuoteUseCase() } returns quote
        //When
        quoteViewModel.randomQuote()
        //Then
        assert(quoteViewModel.quote.value == quote)
    }

    @Test
    fun `if randomQuoteUseCase return null keep the last value`() = runTest {
        //Given
        val quote = Quote("HOLA", "CESAR")
        quoteViewModel.quote.value = quote
        coEvery { getRandomQuoteUseCase() } returns null
        //When
        quoteViewModel.randomQuote()
        //Then
        assert(quoteViewModel.quote.value == quote)
    }

}