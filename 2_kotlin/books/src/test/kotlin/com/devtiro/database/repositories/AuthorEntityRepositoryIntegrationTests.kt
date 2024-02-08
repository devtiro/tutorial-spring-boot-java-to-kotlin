package com.devtiro.database.repositories

import com.devtiro.database.TestDataUtil
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorEntityRepositoryIntegrationTests @Autowired constructor(private val underTest: AuthorRepository) {
    @Test
    fun testThatAuthorCanBeCreatedAndRecalled() {
        val authorEntity = TestDataUtil.createTestAuthorEntityA()
        underTest.save(authorEntity)
        val result = underTest.findById(authorEntity.id!!)
        Assertions.assertThat(result).isPresent()
        Assertions.assertThat(result.get()).isEqualTo(authorEntity)
    }

    @Test
    fun testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        val authorEntityA = TestDataUtil.createTestAuthorEntityA()
        underTest.save(authorEntityA)
        val authorEntityB = TestDataUtil.createTestAuthorB()
        underTest.save(authorEntityB)
        val authorEntityC = TestDataUtil.createTestAuthorC()
        underTest.save(authorEntityC)

        val result = underTest.findAll()
        Assertions.assertThat(result)
            .hasSize(3).containsExactly(authorEntityA, authorEntityB, authorEntityC)
    }

    @Test
    fun testThatAuthorCanBeUpdated() {
        val authorEntityA = TestDataUtil.createTestAuthorEntityA()
        underTest.save(authorEntityA)
        authorEntityA!!.name = "UPDATED"
        underTest.save(authorEntityA)
        val result = underTest.findById(authorEntityA.id!!)
        Assertions.assertThat(result).isPresent()
        Assertions.assertThat(result.get()).isEqualTo(authorEntityA)
    }

    @Test
    fun testThatAuthorCanBeDeleted() {
        val authorEntityA = TestDataUtil.createTestAuthorEntityA()
        underTest.save(authorEntityA)
        underTest.deleteById(authorEntityA.id!!)
        val result = underTest.findById(authorEntityA.id!!)
        Assertions.assertThat(result).isEmpty()
    }

    @Test
    fun testThatGetAuthorsWithAgeLessThan() {
        val testAuthorAEntity = TestDataUtil.createTestAuthorEntityA()
        underTest.save(testAuthorAEntity)
        val testAuthorBEntity = TestDataUtil.createTestAuthorB()
        underTest.save(testAuthorBEntity)
        val testAuthorCEntity = TestDataUtil.createTestAuthorC()
        underTest.save(testAuthorCEntity)

        val result = underTest.ageLessThan(50)
        Assertions.assertThat(result).containsExactly(testAuthorBEntity, testAuthorCEntity)
    }

    @Test
    fun testThatGetAuthorsWithAgeGreaterThan() {
        val testAuthorAEntity = TestDataUtil.createTestAuthorEntityA()
        underTest.save(testAuthorAEntity)
        val testAuthorBEntity = TestDataUtil.createTestAuthorB()
        underTest.save(testAuthorBEntity)
        val testAuthorCEntity = TestDataUtil.createTestAuthorC()
        underTest.save(testAuthorCEntity)

        val result = underTest.findAuthorsWithAgeGreaterThan(50)
        Assertions.assertThat(result).containsExactly(testAuthorAEntity)
    }
}
