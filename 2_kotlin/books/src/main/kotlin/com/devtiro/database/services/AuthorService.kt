package com.devtiro.database.services

import com.devtiro.database.domain.entities.AuthorEntity
import java.util.*

interface AuthorService {
    fun save(authorEntity: AuthorEntity): AuthorEntity

    fun findAll(): List<AuthorEntity>

    fun findOne(id: Long): AuthorEntity?

    fun isExists(id: Long): Boolean

    fun partialUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity

    fun delete(id: Long)
}
