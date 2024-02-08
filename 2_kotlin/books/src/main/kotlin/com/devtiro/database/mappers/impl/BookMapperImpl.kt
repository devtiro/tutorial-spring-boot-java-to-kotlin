package com.devtiro.database.mappers.impl

import com.devtiro.database.domain.dto.AuthorDto
import com.devtiro.database.domain.dto.BookDto
import com.devtiro.database.domain.entities.AuthorEntity
import com.devtiro.database.domain.entities.BookEntity
import com.devtiro.database.mappers.Mapper
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class BookMapperImpl(private val authorMapper: Mapper<AuthorEntity, AuthorDto>) : Mapper<BookEntity, BookDto> {
    override fun mapTo(bookEntity: BookEntity): BookDto {
        return BookDto(
            bookEntity.isbn,
            bookEntity.title,
            bookEntity.authorEntity?.let { authorMapper.mapTo(it) }
        )
    }

    override fun mapFrom(bookDto: BookDto): BookEntity {
        return BookEntity(
            bookDto.isbn,
            bookDto.title,
            bookDto.author?.let { authorMapper.mapFrom(it) }
        )
    }
}
