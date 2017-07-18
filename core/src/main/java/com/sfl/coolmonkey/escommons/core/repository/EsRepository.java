package com.sfl.coolmonkey.escommons.core.repository;

import com.sfl.coolmonkey.escommons.core.model.document.AbstractEsDocument;
import com.sfl.coolmonkey.escommons.core.model.response.DocumentsAndTotalCount;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 3:37 PM
 *
 * @param <T> the document which is subtype of AbstractEsDocument
 */
public interface EsRepository<T extends AbstractEsDocument> {

    /**
     * Saves single document.
     *
     * @param document  the document to save
     * @param indexName the index name where the document should be saved
     * @return the document itself
     */
    @Nonnull
    T save(@Nonnull final T document, @Nonnull final String indexName);

    /**
     * Save all documents.
     *
     * @param documents the documents to save
     * @param indexName the index name where the documents should be saved
     * @return the list of documents itself
     */
    @Nonnull
    List<T> save(@Nonnull final List<T> documents, @Nonnull final String indexName);

    /**
     * Deletes document by id.
     *
     * @param id        the document id
     * @param indexName the index name
     * @return the document id itself
     */
    @Nonnull
    String delete(@Nonnull final String id, @Nonnull final String indexName);

    /**
     * Deletes all documents by list of ids.
     *
     * @param ids       the document ids
     * @param indexName the index name
     * @return the list of ids itself
     */
    @Nonnull
    List<String> delete(@Nonnull final List<String> ids, @Nonnull final String indexName);

    /**
     * Finds document by given id.
     *
     * @param id        the document id
     * @param indexName the index name
     * @return the t
     */
    @Nonnull
    Optional<T> findById(@Nonnull final String id, @Nonnull final String indexName);

    /**
     * Finds documents by ids.
     *
     * @param ids       the ids
     * @param indexName the index name
     * @return the founded documents and total count
     */
    @Nonnull
    DocumentsAndTotalCount<T> findByIds(@Nonnull final List<String> ids, @Nonnull final String indexName);

    /**
     * Finds documents by field map.
     *
     * @param searchField the search field
     * @param terms       the terms
     * @param resultField the result field
     * @return the map where the key is search field and the value is result field
     */
    @Nonnull
    Map<Object, Object> findByField(@Nonnull final String searchField, @Nonnull final List<Object> terms, @Nonnull final String resultField, @Nonnull final String indexName);
}
