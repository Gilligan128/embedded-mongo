package com.henke.embeddedmongo

import com.mongodb.MongoClient
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Configuration
@EnableAutoConfiguration
@AutoConfigureBefore(MongoAutoConfiguration::class)
open class EmbeddedMongoForUnitTestConfiguration {

    @Bean
    open fun mongodExecutable(): MongodExecutable {
        return MongoExecutable
    }

    @Bean(destroyMethod = "invoke")
    @DependsOn("mongodExecutable")
    open fun cleanup(mongoTemplate: MongoTemplate): () -> Boolean {
        return {
            mongoTemplate.db.drop()
            true
        }
    }

    companion object {
        val MongoExecutable: MongodExecutable by lazy {
            val config = MongodConfigBuilder()
                    .net(Net(MongoProperties.DEFAULT_PORT, Network.localhostIsIPv6()))
                    .version(Version.Main.PRODUCTION)
                    .build()
            val executable = MongodStarter.getDefaultInstance().prepare(config)
            executable.start()
            Runtime.getRuntime().addShutdownHook(Thread() {
                @Override
                fun run() {
                    executable.stop()
                }
            })
            executable
        }
    }

}
