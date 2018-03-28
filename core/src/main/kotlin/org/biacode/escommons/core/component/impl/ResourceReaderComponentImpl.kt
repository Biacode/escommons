package org.biacode.escommons.core.component.impl

import org.apache.commons.io.IOUtils
import org.biacode.escommons.core.component.ResourceReaderComponent
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
class ResourceReaderComponentImpl : ResourceReaderComponent {

    //region Public methods
    override fun asString(path: String): Optional<String> {
        return Optional.of(IOUtils.toString(
                javaClass.classLoader.getResourceAsStream(path) ?: return Optional.empty(),
                Charset.defaultCharset()
        ))
    }
    //endregion
}