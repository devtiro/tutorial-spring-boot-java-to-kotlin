package com.devtiro.database.repositories

import com.devtiro.database.domain.entities.AuthorEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : CrudRepository<AuthorEntity?, Long?> {
    fun ageLessThan(age: Int): Iterable<AuthorEntity?>?

    @Query("SELECT a from AuthorEntity a where a.age > ?1")
    fun findAuthorsWithAgeGreaterThan(age: Int): Iterable<AuthorEntity?>?
}
