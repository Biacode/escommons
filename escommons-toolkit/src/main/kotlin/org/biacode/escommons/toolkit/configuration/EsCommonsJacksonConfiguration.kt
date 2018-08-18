package org.biacode.escommons.toolkit.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 5/17/18
 * Time: 5:22 PM
 */
@Configuration
class EsCommonsJacksonConfiguration {

    //region Public methods
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
                .registerModule(KotlinModule())
                .registerModule(Jdk8Module())
                .registerModule(JavaTimeModule())
    }
    //endregion

}