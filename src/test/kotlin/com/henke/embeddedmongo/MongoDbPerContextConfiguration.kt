package com.henke.embeddedmongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Configuration
@EnableAutoConfiguration
class MongoDbPerContextConfiguration {

    @Autowired
    lateinit var  mongoTemplate: MongoTemplate

    @PreDestroy
    open fun cleanDatabase() {
        mongoTemplate.collectionNames.forEach { mongoTemplate.dropCollection(it)}
    }
}