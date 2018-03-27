package org.biacode.escommons.core.component

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
interface MappingsComponent {
    /**
     * Read mappings for index name and type.
     *
     * @param aliasName the index name
     * @param type      the type
     * @return the mappings
     */
    fun readMappings(aliasName: String, type: String): String
}
