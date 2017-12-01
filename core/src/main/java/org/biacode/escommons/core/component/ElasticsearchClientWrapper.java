package org.biacode.escommons.core.component;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public interface ElasticsearchClientWrapper {
    /**
     * Gets cluster indices
     *
     * @return cluster indices
     */
    @Nonnull
    Set<String> getClusterIndices();

    /**
     * Creates new index with the given name
     *
     * @param indexName the index name
     * @return true if index created, otherwise false
     */
    boolean createIndex(@Nonnull final String indexName);

    /**
     * Creates new index with the given name
     *
     * @param indexName the index name
     * @param settings  the settings
     * @return true if index created, otherwise false
     */
    boolean createIndex(@Nonnull final String indexName, @Nonnull Map<String, Object> settings);

    /**
     * Add mappings for the given type of documents in the given index
     *
     * @param indexName    the index name
     * @param documentType the document type
     * @param mappings     mappings
     * @return true if mappings added successfully, otherwise false
     */
    boolean putMapping(@Nonnull final String indexName, @Nonnull final String documentType, @Nonnull final String mappings);

    /**
     * Makes sure data in given index is persisted and is ready for search before returning
     *
     * @param indexName the index name
     */
    void refreshIndex(@Nonnull final String indexName);

    /**
     * Assigned an alias with the given name to the given index
     *
     * @param indexName the index name
     * @param aliasName the alias name
     * @return true if alias assigned to index successfully, otherwise false
     */
    boolean addAlias(@Nonnull final String indexName, @Nonnull final String aliasName);

    /**
     * Deletes the index with the given name
     *
     * @param indexName the index name
     * @return true if index deleted successfully, otherwise false
     */
    boolean deleteIndex(@Nonnull final String indexName);

    /**
     * Checks if index with the given name exists
     *
     * @param indexName the index name
     * @return true if the index with the given name exists, otherwise false
     */
    boolean indexExists(@Nonnull final String indexName);
}
