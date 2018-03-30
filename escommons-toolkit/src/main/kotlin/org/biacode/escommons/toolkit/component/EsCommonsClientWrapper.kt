package org.biacode.escommons.toolkit.component

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
interface EsCommonsClientWrapper {
    fun createIndex(indexName: String, type: String, mappingsName: String): Boolean

    fun getIndices(): Set<String>

    fun refreshIndex(indexName: String)

    fun addAlias(indexName: String, aliasName: String): Boolean

    fun deleteIndices(vararg indexName: String): Boolean

    fun indexExists(indexName: String): Boolean
}
