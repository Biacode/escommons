package org.biacode.escommons.toolkit.component

import org.elasticsearch.common.settings.Settings

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
interface EsCommonsClientWrapper {
    fun createIndex(indexName: String, mappingsName: String): Boolean

    fun createIndex(indexName: String, mappingsName: String, settings: Settings): Boolean

    fun getIndices(): Set<String>

    fun refreshIndex(indexName: String)

    fun addAlias(indexName: String, aliasName: String): Boolean

    fun removeAlias(indexName: String, aliasName: String): Boolean

    fun deleteIndices(vararg indexName: String): Boolean

    fun indexExists(indexName: String): Boolean
}
