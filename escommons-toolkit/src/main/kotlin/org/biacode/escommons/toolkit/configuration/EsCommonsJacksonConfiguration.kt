package org.biacode.escommons.toolkit.configuration

import com.fasterxml.jackson.databind.ObjectMapper
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
    }
    //endregion

}