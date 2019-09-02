package com.henke.embeddedmongo

import org.springframework.data.mongodb.repository.MongoRepository

interface TestRepository: MongoRepository<TestRecord, String>