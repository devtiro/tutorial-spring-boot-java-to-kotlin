package com.devtiro.database.controllers

import com.devtiro.database.TestDataUtil
import com.devtiro.database.services.BookService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@ExtendWith(SpringExtension::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class BooksControllerIntegrationTests @Autowired constructor(
    private val mockMvc: MockMvc,
    private val bookService: BookService
) {
    private val objectMapper = ObjectMapper()

    @Test
    @Throws(Exception::class)
    fun testThatCreateBookReturnsHttpStatus201Created() {
        val testBookA = TestDataUtil.createTestBookDtoA(null)
        val bookJson = objectMapper.writeValueAsString(testBookA)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatUpdateBookReturnsHttpStatus200Ok() {
        val testBookEntityA = TestDataUtil.createTestBookEntityA(null)
        val savedBookEntity = bookService.createUpdateBook(
            testBookEntityA!!.isbn, testBookEntityA
        )

        val testBookA = TestDataUtil.createTestBookDtoA(null)
        testBookA!!.isbn = savedBookEntity.isbn
        val bookJson = objectMapper.writeValueAsString(testBookA)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + savedBookEntity.isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatCreateBookReturnsCreatedBook() {
        val testBookA = TestDataUtil.createTestBookDtoA(null)
        val bookJson = objectMapper.writeValueAsString(testBookA)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic")
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatUpdateBookReturnsUpdatedBook() {
        val testBookEntityA = TestDataUtil.createTestBookEntityA(null)
        val savedBookEntity = bookService.createUpdateBook(
            testBookEntityA!!.isbn, testBookEntityA
        )

        val testBookA = TestDataUtil.createTestBookDtoA(null)
        testBookA!!.isbn = savedBookEntity.isbn
        testBookA.title = "UPDATED"
        val bookJson = objectMapper.writeValueAsString(testBookA)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + savedBookEntity.isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        )
    }


    @Test
    @Throws(Exception::class)
    fun testThatListBooksReturnsHttpStatus200Ok() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatListBooksReturnsBook() {
        val testBookEntityA = TestDataUtil.createTestBookEntityA(null)
        bookService.createUpdateBook(testBookEntityA!!.isbn, testBookEntityA)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-0")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].title").value("The Shadow in the Attic")
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatGetBookReturnsHttpStatus200OkWhenBookExists() {
        val testBookEntityA = TestDataUtil.createTestBookEntityA(null)
        bookService.createUpdateBook(testBookEntityA!!.isbn, testBookEntityA)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/" + testBookEntityA.isbn)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatGetBookReturnsHttpStatus404WhenBookDoesntExist() {
        val testBookEntityA = TestDataUtil.createTestBookEntityA(null)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/" + testBookEntityA!!.isbn)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatPartialUpdateBookReturnsHttpStatus200Ok() {
        val testBookEntityA = TestDataUtil.createTestBookEntityA(null)
        bookService.createUpdateBook(testBookEntityA!!.isbn, testBookEntityA)

        val testBookA = TestDataUtil.createTestBookDtoA(null)
        testBookA!!.title = "UPDATED"
        val bookJson = objectMapper.writeValueAsString(testBookA)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/books/" + testBookEntityA.isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatPartialUpdateBookReturnsUpdatedBook() {
        val testBookEntityA = TestDataUtil.createTestBookEntityA(null)
        bookService.createUpdateBook(testBookEntityA!!.isbn, testBookEntityA)

        val testBookA = TestDataUtil.createTestBookDtoA(null)
        testBookA!!.title = "UPDATED"
        val bookJson = objectMapper.writeValueAsString(testBookA)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/books/" + testBookEntityA.isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntityA.isbn)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatDeleteNonExistingBookReturnsHttpStatus204NoContent() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/books/kjsbdfjdfsk")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    @Test
    @Throws(Exception::class)
    fun testThatDeleteExistingBookReturnsHttpStatus204NoContent() {
        val testBookEntityA = TestDataUtil.createTestBookEntityA(null)
        bookService.createUpdateBook(testBookEntityA!!.isbn, testBookEntityA)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/books/" + testBookEntityA.isbn)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent())
    }
}
