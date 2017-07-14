package com.sfl.coolmonkey.escommons.core.component.impl;

import com.sfl.coolmonkey.escommons.core.component.ElasticsearchClientWrapper;
import com.sfl.coolmonkey.escommons.core.component.IndexNameGenerationComponent;
import com.sfl.coolmonkey.escommons.core.component.IndexingComponent;
import com.sfl.coolmonkey.escommons.core.component.MappingsComponent;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Arthur Asatryan
 * Company: SFL LLC
 * Date: 2/29/16
 * Time: 6:49 PM
 */
@Component
public class IndexingComponentImpl implements IndexingComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexingComponentImpl.class);

    //region Dependencies
    @Autowired
    private MappingsComponent mappingsComponent;

    @Autowired
    private IndexNameGenerationComponent indexNameGenerationComponent;

    @Autowired
    private ElasticsearchClientWrapper elasticsearchClientWrapper;
    //endregion

    //region Constructors
    public IndexingComponentImpl() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @Nonnull
    @Override
    public String createIndexAndSetupMappings(@Nonnull final String originalIndex, @Nonnull final List<String> types) {
        assertOriginalIndexNotNull(originalIndex);
        Assert.notNull(types, "The list of types should not be null");
        final String newIndexName = indexNameGenerationComponent.generateNameForGivenIndex(originalIndex);
        elasticsearchClientWrapper.createIndex(newIndexName);
        types.forEach(type -> elasticsearchClientWrapper.putMapping(newIndexName, type, mappingsComponent.readMappings(originalIndex, type)));
        elasticsearchClientWrapper.refreshIndex(newIndexName);
        return newIndexName;
    }

    @Override
    public void createAliasAndDeleteOldIndices(@Nonnull final String originalIndex, @Nonnull final String newIndex) {
        assertOriginalIndexNotNull(originalIndex);
        assertNewIndexNotNull(newIndex);
        elasticsearchClientWrapper.addAlias(newIndex, originalIndex);
        elasticsearchClientWrapper.getClusterIndices()
                .stream()
                .filter(indexName -> ObjectUtils.notEqual(indexName, newIndex))
                .filter(indexName -> ObjectUtils.notEqual(indexName, originalIndex))
                .filter(indexName -> indexName.startsWith(originalIndex))
                .forEach(elasticsearchClientWrapper::deleteIndex);
    }

    @Override
    public void removeIndexByName(@Nonnull final String indexName) {
        Assert.notNull(indexName, "Index name should not be null.");
        elasticsearchClientWrapper.getClusterIndices()
                .stream()
                .filter(indexName::equals)
                .forEach(elasticsearchClientWrapper::deleteIndex);
    }
    //endregion

    //region Utility methods
    private void assertOriginalIndexNotNull(final String originalIndex) {
        Assert.notNull(originalIndex, "The original index should not be null");
    }

    private void assertNewIndexNotNull(final String newIndex) {
        Assert.notNull(newIndex, "The new index should not be null");
    }
    //endregion
}
