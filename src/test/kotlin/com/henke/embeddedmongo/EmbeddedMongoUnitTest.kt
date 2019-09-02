package com.henke.embeddedmongo

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration

@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
@ContextConfiguration(classes = [EmbeddedMongoForUnitTestConfiguration::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //Ensures we clean the db before each test
annotation class EmbeddedMongoUnitTest
