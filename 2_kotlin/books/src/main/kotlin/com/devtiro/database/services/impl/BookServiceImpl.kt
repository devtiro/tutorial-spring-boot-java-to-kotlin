package com.devtiro.database.services.impl

import com.devtiro.database.domain.entities.BookEntity
import com.devtiro.database.repositories.BookRepository
import com.devtiro.database.services.BookService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {
    override fun createUpdateBook(isbn: String?, book: BookEntity): BookEntity {
        book.isbn = isbn
        return bookRepository.save(book)
    }

    override fun findAll(): List<BookEntity> {
        return bookRepository.findAll().toList().filterNotNull()
    }

    override fun findOne(isbn: String): BookEntity? {
        return bookRepository.findByIdOrNull(isbn)
    }

    override fun isExists(isbn: String): Boolean {
        return bookRepository.existsById(isbn)
    }

    override fun partialUpdate(isbn: String, bookEntity: BookEntity): BookEntity {
        bookEntity.isbn = isbn

        return bookRepository.findByIdOrNull(isbn)?.let { existingBook ->
            bookEntity.title?.let { existingBook.title = it }
            bookRepository.save(existingBook)
        } ?: throw RuntimeException("Book does not exist")

    }

    override fun delete(isbn: String) {
        bookRepository.deleteById(isbn)
    }
}
