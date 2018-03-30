package org.biacode.escommons.toolkit.component.impl

import org.biacode.escommons.core.exception.EsCommonsCoreRuntimeException
import org.biacode.escommons.toolkit.component.MappingsComponent
import org.biacode.escommons.toolkit.component.ResourceReaderComponent
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
    override fun readMappings(mappingName: String): String {
        return resourceReaderComponent.asString("mappings/$mappingName.json").orElseThrow {
            LOGGER.error("Can not read mappings with name - {}", mappingName)
            EsCommonsCoreRuntimeException("Can not read mappings with name - $mappingName")
        }
    }
    //endregion

    //region Utility methods
    companion object {
        private val LOGGER = LoggerFactory.getLogger(MappingsComponentImpl::class.java)
    }
    //endregion
}
