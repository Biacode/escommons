package org.biacode.escommons.toolkit.component.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.biacode.escommons.toolkit.component.JsonComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.InputStream

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
class JsonComponentImpl : JsonComponent {

    //region Dependencies
    @Autowired
    lateinit var objectMapper: ObjectMapper
    //endregion

    //region Public methods
    override fun <T> deserializeFromString(source: String, clazz: Class<T>): T {
        return objectMapper.reader().forType(clazz).readValue(source)
    }

    override fun <T> deserializeFromInputStream(source: InputStream, clazz: Class<T>): T {
        return objectMapper.reader().forType(clazz).readValue(source)
    }

    override fun <T> deserializeFromInputStreamWithTypeReference(source: InputStream, typeReference: TypeReference<T>): T {
        return objectMapper.reader().forType(typeReference).readValue(source)
    }

    override fun serialize(source: Any): String {
        return objectMapper.writeValueAsString(source)
    }
    //endregion
}
