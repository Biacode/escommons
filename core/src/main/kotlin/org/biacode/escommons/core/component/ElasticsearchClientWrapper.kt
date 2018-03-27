package org.biacode.escommons.core.component

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
interface ElasticsearchClientWrapper {
    fun clusterIndices(): Set<String>

    fun createIndex(indexName: String, type: String, aliasName: String): Boolean

    fun refreshIndex(indexName: String)

    fun addAlias(indexName: String, aliasName: String): Boolean

    fun deleteIndex(indexName: String): Boolean

    fun indexExists(indexName: String): Boolean
}
