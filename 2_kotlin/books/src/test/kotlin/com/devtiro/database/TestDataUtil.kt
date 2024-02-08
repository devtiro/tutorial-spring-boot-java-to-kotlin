package com.devtiro.database

import com.devtiro.database.domain.dto.AuthorDto
import com.devtiro.database.domain.dto.BookDto
import com.devtiro.database.domain.entities.AuthorEntity
import com.devtiro.database.domain.entities.BookEntity

object TestDataUtil {
    fun createTestAuthorEntityA(): AuthorEntity {
        return AuthorEntity.builder()
            .id(1L)
            .name("Abigail Rose")
            .age(80)
            .build()
    }

    fun createTestAuthorDtoA(): AuthorDto {
        return AuthorDto.builder()
            .id(1L)
            .name("Abigail Rose")
            .age(80)
            .build()
    }

    fun createTestAuthorB(): AuthorEntity {
        return AuthorEntity.builder()
            .id(2L)
            .name("Thomas Cronin")
            .age(44)
            .build()
    }

    fun createTestAuthorC(): AuthorEntity {
        return AuthorEntity.builder()
            .id(3L)
            .name("Jesse A Casey")
            .age(24)
            .build()
    }

    fun createTestBookEntityA(authorEntity: AuthorEntity?): BookEntity {
        return BookEntity.builder()
            .isbn("978-1-2345-6789-0")
            .title("The Shadow in the Attic")
            .authorEntity(authorEntity)
            .build()
    }

    fun createTestBookDtoA(authorDto: AuthorDto?): BookDto {
        return BookDto.builder()
            .isbn("978-1-2345-6789-0")
            .title("The Shadow in the Attic")
            .author(authorDto)
            .build()
    }

    fun createTestBookB(authorEntity: AuthorEntity?): BookEntity {
        return BookEntity.builder()
            .isbn("978-1-2345-6789-1")
            .title("Beyond the Horizon")
            .authorEntity(authorEntity)
            .build()
    }

    fun createTestBookC(authorEntity: AuthorEntity?): BookEntity {
        return BookEntity.builder()
            .isbn("978-1-2345-6789-2")
            .title("The Last Ember")
            .authorEntity(authorEntity)
            .build()
    }
}
