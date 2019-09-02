package com.henke.embeddedmongo

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@EmbeddedMongoUnitTest
class EmbeddedMongoUnitTestsAreIsolated {

    @Autowired
    private lateinit var testRepository: TestRepository

    @Test
    fun `there should only be one record`() {
        testRepository.save(TestRecord())
        Assert.assertEquals(1, testRepository.count())
    }

    @Test
    fun `there should still only be one record`() {
        testRepository.save(TestRecord())
        Assert.assertEquals(1, testRepository.count())
    }
}