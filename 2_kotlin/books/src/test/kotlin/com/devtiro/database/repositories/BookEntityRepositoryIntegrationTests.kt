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
class BookEntityRepositoryIntegrationTests @Autowired constructor(private val underTest: BookRepository) {
    @Test
    fun testThatBookCanBeCreatedAndRecalled() {
        val authorEntity = TestDataUtil.createTestAuthorEntityA()
        val bookEntity = TestDataUtil.createTestBookEntityA(authorEntity)
        underTest.save(bookEntity)
        val result = underTest.findById(bookEntity.isbn!!)
        Assertions.assertThat(result).isPresent()
        Assertions.assertThat(result.get()).isEqualTo(bookEntity)
    }

    @Test
    fun testThatMultipleBooksCanBeCreatedAndRecalled() {
        val authorEntity = TestDataUtil.createTestAuthorEntityA()

        val bookEntityA = TestDataUtil.createTestBookEntityA(authorEntity)
        underTest.save(bookEntityA)

        val bookEntityB = TestDataUtil.createTestBookB(authorEntity)
        underTest.save(bookEntityB)

        val bookEntityC = TestDataUtil.createTestBookC(authorEntity)
        underTest.save(bookEntityC)

        val result = underTest.findAll()
        Assertions.assertThat(result)
            .hasSize(3)
            .containsExactly(bookEntityA, bookEntityB, bookEntityC)
    }

    @Test
    fun testThatBookCanBeUpdated() {
        val authorEntity = TestDataUtil.createTestAuthorEntityA()

        val bookEntityA = TestDataUtil.createTestBookEntityA(authorEntity)
        underTest.save(bookEntityA)

        bookEntityA!!.title = "UPDATED"
        underTest.save(bookEntityA)

        val result = underTest.findById(bookEntityA.isbn!!)
        Assertions.assertThat(result).isPresent()
        Assertions.assertThat(result.get()).isEqualTo(bookEntityA)
    }

    @Test
    fun testThatBookCanBeDeleted() {
        val authorEntity = TestDataUtil.createTestAuthorEntityA()

        val bookEntityA = TestDataUtil.createTestBookEntityA(authorEntity)
        underTest.save(bookEntityA)

        underTest.deleteById(bookEntityA.isbn!!)

        val result = underTest.findById(bookEntityA.isbn!!)
        Assertions.assertThat(result).isEmpty()
    }
}
