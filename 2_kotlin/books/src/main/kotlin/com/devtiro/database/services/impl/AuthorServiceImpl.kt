package com.devtiro.database.services.impl

import com.devtiro.database.domain.entities.AuthorEntity
import com.devtiro.database.repositories.AuthorRepository
import com.devtiro.database.services.AuthorService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class AuthorServiceImpl(private val authorRepository: AuthorRepository) : AuthorService {
    override fun save(authorEntity: AuthorEntity): AuthorEntity {
        return authorRepository.save(authorEntity)
    }

    override fun findAll(): List<AuthorEntity> {
        return authorRepository.findAll().toList().filterNotNull()
    }

    override fun findOne(id: Long): AuthorEntity? {
        return authorRepository.findByIdOrNull(id)
    }

    override fun isExists(id: Long): Boolean {
        return authorRepository.existsById(id)
    }

    override fun partialUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity {
        authorEntity.id = id

        return authorRepository.findByIdOrNull(id)?.let { existingAuthor ->
            authorEntity.name?.let { existingAuthor.name = it }
            authorEntity.age?.let { existingAuthor.age = it }
            authorRepository.save(existingAuthor)
        } ?: throw RuntimeException("Author does not exist")
    }

    override fun delete(id: Long) {
        authorRepository.deleteById(id)
    }
}
