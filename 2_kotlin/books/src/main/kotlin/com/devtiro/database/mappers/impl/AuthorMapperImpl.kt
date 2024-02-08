package com.devtiro.database.mappers.impl

import com.devtiro.database.domain.dto.AuthorDto
import com.devtiro.database.domain.entities.AuthorEntity
import com.devtiro.database.mappers.Mapper
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class AuthorMapperImpl(private val modelMapper: ModelMapper) : Mapper<AuthorEntity, AuthorDto> {
    override fun mapTo(authorEntity: AuthorEntity): AuthorDto {
        return AuthorDto(
            authorEntity.id,
            authorEntity.name,
            authorEntity.age
        )
    }

    override fun mapFrom(authorDto: AuthorDto): AuthorEntity {
        return AuthorEntity(
            authorDto.id,
            authorDto.name,
            authorDto.age
        )
    }
}
