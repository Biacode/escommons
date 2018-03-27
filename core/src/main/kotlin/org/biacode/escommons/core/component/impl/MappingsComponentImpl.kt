package org.biacode.escommons.core.component.impl

import org.biacode.escommons.core.component.MappingsComponent
import org.biacode.escommons.core.component.ResourceReaderComponent
import org.biacode.escommons.core.exception.EsCoreRuntimeException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
class MappingsComponentImpl : MappingsComponent {

    //region Dependencies
    @Autowired
    private lateinit var resourceReaderComponent: ResourceReaderComponent
    //endregion

    //region Public methods
    override fun readMappings(aliasName: String, type: String): String {
        return resourceReaderComponent.asString(getMappingPath(aliasName, type)).orElseThrow {
            LOGGER.error("Can not read mappings for alias name - {} and document type - {}", aliasName, type)
            EsCoreRuntimeException("Can not read mappings for alias name and document type")
        }
    }
    //endregion

    //region Utility methods
    private fun getMappingPath(aliasName: String, type: String): String {
        return "mappings/$aliasName/$type.json"
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MappingsComponentImpl::class.java)
    }
    //endregion
}
