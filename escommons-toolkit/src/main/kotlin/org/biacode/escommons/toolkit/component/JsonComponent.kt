package org.biacode.escommons.toolkit.component

import com.fasterxml.jackson.core.type.TypeReference
import java.io.InputStream

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
interface JsonComponent {
    /**
     * Deserialize from String source.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param clazz  the clazz
     * @return the deserializeFromString object
    </T> */
    fun <T> deserializeFromString(source: String, clazz: Class<T>): T

    /**
     * Deserialize from InputStream source.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param clazz  the clazz
     * @return the deserializeFromString object
    </T> */
    fun <T> deserializeFromInputStream(source: InputStream, clazz: Class<T>): T

    /**
     * Deserialize from InputStream source and type reference.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param typeReference  the type reference
     * @return the deserializeFromString object
    </T> */
    fun <T> deserializeFromInputStreamWithTypeReference(source: InputStream, typeReference: TypeReference<T>): T

    /**
     * Serialize object to string
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param clazz  the clazz
     * @return the serialize string
    </T> */
    fun serialize(source: Any): String
}
