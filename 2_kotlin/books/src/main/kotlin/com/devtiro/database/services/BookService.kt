package com.devtiro.database.services

import com.devtiro.database.domain.entities.BookEntity
import java.util.*

interface BookService {
    fun createUpdateBook(isbn: String?, book: BookEntity): BookEntity

    fun findAll(): List<BookEntity>
    fun findOne(isbn: String): BookEntity?

    fun isExists(isbn: String): Boolean

    fun partialUpdate(isbn: String, bookEntity: BookEntity): BookEntity

    fun delete(isbn: String)
}
