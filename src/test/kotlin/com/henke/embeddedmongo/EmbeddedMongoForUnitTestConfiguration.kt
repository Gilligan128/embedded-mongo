package com.henke.embeddedmongo

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import org.springframework.test.context.TestContextManager
import java.util.*

@Configuration
@EnableAutoConfiguration
open class EmbeddedMongoForUnitTestConfiguration {

    @Bean
    open fun mongodExecutable(): MongodExecutable {
        return MongoExecutable
    }

    //We cleanup the database to keep the memory footprint low when case we run hundreds of unit tests
    @Bean(destroyMethod = "invoke")
    @DependsOn("mongodExecutable")
    open fun cleanup(mongoTemplate: MongoTemplate): () -> Boolean {
        return {
            mongoTemplate.db.drop()
            true
        }
    }

    @Bean
    @DependsOn("mongodExecutable")
    open fun dbFactory(): SimpleMongoDbFactory {
        //we create a unique database per test in case of parallel execution.
        return SimpleMongoDbFactory(MongoClientURI("mongodb://localhost/db-${UUID.randomUUID()}"))
    }

    companion object {
        //the server is a singleton as it is slow to startup. It should startup only once per test run.
        val MongoExecutable: MongodExecutable by lazy {
            val config = MongodConfigBuilder()
                    .net(Net(MongoProperties.DEFAULT_PORT, Network.localhostIsIPv6()))
                    .version(Version.Main.PRODUCTION)
                    .build()
            val executable = MongodStarter.getDefaultInstance().prepare(config)
            executable.start()
            ensureServerWillStop(executable)
            executable
        }

        private fun ensureServerWillStop(executable: MongodExecutable) {
            Runtime.getRuntime().addShutdownHook(Thread() {
                @Override
                fun run() {
                    executable.stop()
                }
            })
        }
    }

}
