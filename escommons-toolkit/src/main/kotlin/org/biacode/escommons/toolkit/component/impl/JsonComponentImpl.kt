package org.biacode.escommons.toolkit.component.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.biacode.escommons.toolkit.component.JsonComponent
import org.springframework.stereotype.Component
import java.io.InputStream

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
class JsonComponentImpl : JsonComponent {

    //region Public methods
    override fun <T> deserializeFromString(source: String, clazz: Class<T>): T {
        return OBJECT_MAPPER.reader().forType(clazz).readValue(source)
    }

    override fun <T> deserializeFromInputStream(source: InputStream, clazz: Class<T>): T {
        return OBJECT_MAPPER.reader().forType(clazz).readValue(source)
    }

    override fun <T> deserializeFromInputStreamWithTypeReference(source: InputStream, typeReference: TypeReference<T>): T {
        return OBJECT_MAPPER.reader().forType(typeReference).readValue(source)
    }

    override fun <T> serialize(source: T, clazz: Class<T>): String {
        return OBJECT_MAPPER.writer().forType(clazz).writeValueAsString(source)
    }
    //endregion

    //region Companion objects
    companion object {
        private val OBJECT_MAPPER = ObjectMapper()
    }
    //endregion
}
