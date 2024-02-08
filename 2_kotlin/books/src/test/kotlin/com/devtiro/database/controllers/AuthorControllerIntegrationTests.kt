package com.devtiro.database.controllers

import com.devtiro.database.TestDataUtil
import com.devtiro.database.services.AuthorService
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
class AuthorControllerIntegrationTests @Autowired constructor(
    private val mockMvc: MockMvc,
    private val authorService: AuthorService
) {
    private val objectMapper = ObjectMapper()

    @Test
    @Throws(Exception::class)
    fun testThatCreateAuthorSuccessfullyReturnsHttp201Created() {
        val testAuthorA = TestDataUtil.createTestAuthorEntityA()
        testAuthorA!!.id = null
        val authorJson = objectMapper.writeValueAsString(testAuthorA)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatCreateAuthorSuccessfullyReturnsSavedAuthor() {
        val testAuthorA = TestDataUtil.createTestAuthorDtoA()
        testAuthorA!!.id = null
        val authorJson = objectMapper.writeValueAsString(testAuthorA)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(80)
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatListAuthorsReturnsHttpStatus200() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @Throws(Exception::class)
    fun testThatListAuthorsReturnsListOfAuthors() {
        val testAuthorEntityA = TestDataUtil.createTestAuthorEntityA()
        authorService.save(testAuthorEntityA)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatGetAuthorReturnsHttpStatus200WhenAuthorExist() {
        val testAuthorEntityA = TestDataUtil.createTestAuthorEntityA()
        authorService.save(testAuthorEntityA)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @Throws(Exception::class)
    fun testThatGetAuthorReturnsAuthorWhenAuthorExist() {
        val testAuthorEntityA = TestDataUtil.createTestAuthorEntityA()
        authorService.save(testAuthorEntityA)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(80)
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatGetAuthorReturnsHttpStatus404WhenNoAuthorExists() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    @Test
    @Throws(Exception::class)
    fun testThatFullUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() {
        val testAuthorDtoA = TestDataUtil.createTestAuthorDtoA()
        val authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA)
        mockMvc.perform(
            MockMvcRequestBuilders.put("/authors/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    @Test
    @Throws(Exception::class)
    fun testThatFullUpdateAuthorReturnsHttpStatus4200WhenAuthorExists() {
        val testAuthorEntityA = TestDataUtil.createTestAuthorEntityA()
        val savedAuthor = authorService.save(testAuthorEntityA)

        val testAuthorDtoA = TestDataUtil.createTestAuthorDtoA()
        val authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/authors/" + savedAuthor.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @Throws(Exception::class)
    fun testThatFullUpdateUpdatesExistingAuthor() {
        val testAuthorEntityA = TestDataUtil.createTestAuthorEntityA()
        val savedAuthor = authorService.save(testAuthorEntityA)

        val authorDto = TestDataUtil.createTestAuthorB()
        authorDto!!.id = savedAuthor.id

        val authorDtoUpdateJson = objectMapper.writeValueAsString(authorDto)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/authors/" + savedAuthor.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorDtoUpdateJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.id)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value(authorDto.name)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(authorDto.age)
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatPartialUpdateExistingAuthorReturnsHttpStatus20Ok() {
        val testAuthorEntityA = TestDataUtil.createTestAuthorEntityA()
        val savedAuthor = authorService.save(testAuthorEntityA)

        val testAuthorDtoA = TestDataUtil.createTestAuthorDtoA()
        testAuthorDtoA!!.name = "UPDATED"
        val authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/authors/" + savedAuthor.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @Throws(Exception::class)
    fun testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor() {
        val testAuthorEntityA = TestDataUtil.createTestAuthorEntityA()
        val savedAuthor = authorService.save(testAuthorEntityA)

        val testAuthorDtoA = TestDataUtil.createTestAuthorDtoA()
        testAuthorDtoA!!.name = "UPDATED"
        val authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/authors/" + savedAuthor.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.id)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDtoA.age)
        )
    }

    @Test
    @Throws(Exception::class)
    fun testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/authors/999")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    @Test
    @Throws(Exception::class)
    fun testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() {
        val testAuthorEntityA = TestDataUtil.createTestAuthorEntityA()
        val savedAuthor = authorService.save(testAuthorEntityA)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/authors/" + savedAuthor.id)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent())
    }
}
