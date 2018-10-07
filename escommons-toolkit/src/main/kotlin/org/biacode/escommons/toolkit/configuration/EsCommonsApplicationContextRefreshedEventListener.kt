package org.biacode.escommons.toolkit.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import java.time.LocalDateTime

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
        val javaTimeModule = JavaTimeModule()
                .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer.INSTANCE)
                .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer.INSTANCE)
        objectMapper
                .registerModule(javaTimeModule)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }
    //endregion
}