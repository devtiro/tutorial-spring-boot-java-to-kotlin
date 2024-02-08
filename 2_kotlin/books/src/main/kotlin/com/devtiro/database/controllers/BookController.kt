package com.devtiro.database.controllers

import com.devtiro.database.domain.dto.BookDto
import com.devtiro.database.domain.entities.BookEntity
import com.devtiro.database.mappers.Mapper
import com.devtiro.database.services.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
class BookController(private val bookMapper: Mapper<BookEntity, BookDto>, private val bookService: BookService) {
    @PutMapping(path = ["/books/{isbn}"])
    fun createUpdateBook(@PathVariable isbn: String, @RequestBody bookDto: BookDto?): ResponseEntity<BookDto?> {
        val bookEntity = bookMapper.mapFrom(bookDto!!)
        val bookExists = bookService.isExists(isbn)
        val savedBookEntity = bookService.createUpdateBook(isbn, bookEntity)
        val savedUpdatedBookDto = bookMapper.mapTo(savedBookEntity!!)

        return if (bookExists) {
            ResponseEntity(savedUpdatedBookDto, HttpStatus.OK)
        } else {
            ResponseEntity(savedUpdatedBookDto, HttpStatus.CREATED)
        }
    }

    @PatchMapping(path = ["/books/{isbn}"])
    fun partialUpdateBook(
        @PathVariable("isbn") isbn: String,
        @RequestBody bookDto: BookDto?
    ): ResponseEntity<BookDto?> {
        if (!bookService.isExists(isbn)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val bookEntity = bookMapper.mapFrom(bookDto!!)
        val updatedBookEntity = bookService.partialUpdate(isbn, bookEntity)
        return ResponseEntity(
            bookMapper.mapTo(updatedBookEntity!!),
            HttpStatus.OK
        )
    }

    @GetMapping(path = ["/books"])
    fun listBooks(): List<BookDto?> {
        val books = bookService.findAll()
        return books!!.stream()
            .map { a: BookEntity? -> bookMapper.mapTo(a!!) }
            .collect(Collectors.toList())
    }

    @GetMapping(path = ["/books/{isbn}"])
    fun getBook(@PathVariable("isbn") isbn: String): ResponseEntity<BookDto?> {
        return bookService.findOne(isbn)?.let { bookEntity: BookEntity? ->
            val bookDto = bookMapper.mapTo(bookEntity!!)
            ResponseEntity(bookDto, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping(path = ["/books/{isbn}"])
    fun deleteBook(@PathVariable("isbn") isbn: String): ResponseEntity<*> {
        bookService.delete(isbn)
        return ResponseEntity<Any?>(HttpStatus.NO_CONTENT)
    }
}
