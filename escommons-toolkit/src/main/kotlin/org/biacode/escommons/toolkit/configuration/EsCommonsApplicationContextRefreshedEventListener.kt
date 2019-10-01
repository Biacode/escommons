package org.biacode.escommons.toolkit.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent

/**
 * Created by Arthur Asatryan.
 * Date: 5/17/18
 * Time: 5:23 PM
 */
@Configuration
class EsCommonsApplicationContextRefreshedEventListener : ApplicationListener<ContextRefreshedEvent> {

    //region Dependencies
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    //endregion

    //region Public methods
    override fun onApplicationEvent(contextRefreshedEvent: ContextRefreshedEvent) {
        objectMapper
                .registerModule(Jdk8Module())
                .registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .registerModule(ParameterNamesModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }
    //endregion
}