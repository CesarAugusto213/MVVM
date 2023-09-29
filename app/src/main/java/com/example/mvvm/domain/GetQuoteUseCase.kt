package com.example.mvvm.domain

import com.example.mvvm.data.QuoteRepository
import com.example.mvvm.domain.mapper.convertToDatabase
import com.example.mvvm.domain.model.Quote
import javax.inject.Inject

class GetQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {
    suspend operator fun invoke(): List<Quote> {
        val quotes = repository.getAllQuotesFromApi()

        return if(quotes.isNotEmpty()){
            repository.clearQuotes()
            repository.insertQuotes(quotes.convertToDatabase())
            quotes
        } else {
            repository.getAllQuotesFromDatabase()
        }
    }
}