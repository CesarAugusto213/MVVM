package com.example.mvvm.domain.mapper

import com.example.mvvm.data.database.entities.QuoteEntity
import com.example.mvvm.data.model.QuoteModel
import com.example.mvvm.domain.model.Quote

fun QuoteModel.convertToDomain() = Quote(
    quote = quote,
    author = author
)

fun List<QuoteModel>.convertToDomain(): List<Quote> {
    return map(QuoteModel::convertToDomain)
}

fun QuoteEntity.convertToDomain2() = Quote(
    quote = quote,
    author = author
)

fun List<QuoteEntity>.convertToDomain2(): List<Quote> {
    return map(QuoteEntity::convertToDomain2)
}

fun Quote.convertToDatabase() = QuoteEntity(
    quote = quote,
    author = author
)

fun List<Quote>.convertToDatabase(): List<QuoteEntity> {
    return map(Quote::convertToDatabase)
}


