package com.henke.embeddedmongo

import com.mongodb.MongoClient
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
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