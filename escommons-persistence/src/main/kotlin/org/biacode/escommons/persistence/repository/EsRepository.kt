package org.biacode.escommons.persistence.repository

import org.biacode.escommons.core.model.document.AbstractEsDocument
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 3:37 PM
 */
interface EsRepository<T : AbstractEsDocument> {

    /**
     * Saves single document.
     *
     * @param document  the document to save
     * @param indexName the index name where the document should be saved
     * @return executed successfully
     */
    fun save(document: T, indexName: String): Boolean

    /**
     * Save all documents.
     *
     * @param documents the documents to save
     * @param indexName the index name where the documents should be saved
     * @return executed successfully
     */
    fun save(documents: List<T>, indexName: String): Boolean

    /**
     * Deletes document by id.
     *
     * @param id        the document id
     * @param indexName the index name
     * @return executed successfully
     */
    fun delete(id: String, indexName: String): Boolean

    /**
     * Deletes all documents by list of ids.
     *
     * @param ids       the document ids
     * @param indexName the index name
     * @return the list of ids itself
     */
    fun delete(ids: List<String>, indexName: String): Boolean

    /**
     * Finds document by given id.
     *
     * @param id        the document id
     * @param indexName the index name
     * @return the t
     */
    fun findById(id: String, indexName: String): Optional<T>

    /**
     * Finds documents by ids.
     *
     * @param ids       the ids
     * @param indexName the index name
     * @return list of founded documents
     */
    fun findByIds(ids: List<String>, indexName: String): List<T>
}
