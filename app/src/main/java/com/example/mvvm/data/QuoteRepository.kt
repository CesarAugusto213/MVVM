package com.example.mvvm.data

import com.example.mvvm.data.database.dao.QuoteDao
import com.example.mvvm.data.database.entities.QuoteEntity
import com.example.mvvm.data.network.QuoteService
import com.example.mvvm.domain.mapper.convertToDomain
import com.example.mvvm.domain.mapper.convertToDomain2
import com.example.mvvm.domain.model.Quote
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val quoteDao: QuoteDao
) {

    suspend fun getAllQuotesFromApi(): List<Quote> {
        return api.getQuotes().convertToDomain()
    }

    suspend fun getAllQuotesFromDatabase(): List<Quote>{
        return quoteDao.getAllQuotes().convertToDomain2()
    }

    suspend fun insertQuotes(quotes: List<QuoteEntity>){
        quoteDao.insertAll(quotes)
    }

    suspend fun clearQuotes(){
        quoteDao.deleteAllQuotes()
    }

}