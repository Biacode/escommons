package com.sfl.coolmonkey.escommons.core.component.impl;

import com.carrotsearch.hppc.ObjectLookupContainer;
import com.sfl.coolmonkey.escommons.core.component.ElasticsearchClientWrapper;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: Arthur Asatryan
 * Company: SFL LLC
 * Date: 2/29/16
 * Time: 9:15 PM
 */
@Component
public class ElasticsearchClientWrapperImpl implements ElasticsearchClientWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClientWrapperImpl.class);

    //region Dependencies
    @Autowired
    private Client client;
    //endregion

    //region Constructors
    public ElasticsearchClientWrapperImpl() {
        LOGGER.debug("Initializing elasticsearch client wrapper");
    }
    //endregion

    //region Public methods
    @Nonnull
    @Override
    public Set<String> getClusterIndices() {
        final ObjectLookupContainer<String> indices = client.admin().cluster().prepareState()
                .execute().actionGet().getState().getMetaData().indices().keys();
        final Set<String> indicesNames = new HashSet<>();
        for (final Object index : indices.toArray()) {
            indicesNames.add(index.toString());
        }
        return indicesNames;
    }

    @Override
    public boolean createIndex(@Nonnull final String indexName) {
        assertIndexNameNotNull(indexName);
        return client.admin().indices().prepareCreate(indexName).get().isAcknowledged();
    }

    @Override
    public boolean createIndex(@Nonnull final String indexName, @Nonnull final Map<String, Object> settings) {
        assertIndexNameNotNull(indexName);
        Assert.notNull(settings, "The settings should not be null");
        return client.admin().indices().prepareCreate(indexName).setSettings(settings).get().isAcknowledged();
    }

    @Override
    public boolean putMapping(@Nonnull final String indexName, @Nonnull final String documentType, @Nonnull final String mappings) {
        assertIndexNameNotNull(indexName);
        Assert.notNull(documentType, "Document type should not be null");
        Assert.notNull(mappings, "Mappings should not be null");
        return client.admin().indices().preparePutMapping(indexName).setType(documentType).setSource(mappings).get().isAcknowledged();
    }

    @Override
    public void refreshIndex(@Nonnull final String indexName) {
        assertIndexNameNotNull(indexName);
        client.admin().indices().prepareRefresh(indexName).get();
    }

    @Override
    public boolean addAlias(@Nonnull final String indexName, @Nonnull final String aliasName) {
        assertIndexNameNotNull(indexName);
        Assert.notNull(aliasName, "Alias name should not be null");
        return client.admin().indices().prepareAliases().addAlias(indexName, aliasName).get().isAcknowledged();
    }

    @Override
    public boolean deleteIndex(@Nonnull final String indexName) {
        assertIndexNameNotNull(indexName);
        return client.admin().indices().prepareDelete(indexName).get().isAcknowledged();
    }

    @Override
    public boolean indexExists(@Nonnull final String indexName) {
        assertIndexNameNotNull(indexName);
        return client.admin().indices().prepareExists(indexName).get().isExists();
    }
    //endregion

    //region Utility methods
    private void assertIndexNameNotNull(final String indexName) {
        Assert.notNull(indexName, "Index name should not be null");
    }
    //endregion
}
