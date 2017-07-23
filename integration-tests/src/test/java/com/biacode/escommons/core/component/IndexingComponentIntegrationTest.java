package com.biacode.escommons.core.component;

import com.biacode.escommons.core.test.AbstractIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Arthur Asatryan.
 * Date: 7/23/17
 * Time: 9:10 PM
 */
@Component
public class IndexingComponentIntegrationTest extends AbstractIntegrationTest {

    //region Dependencies
    @Autowired
    private ElasticsearchClientWrapper elasticsearchClientWrapper;

    @Autowired
    private IndexingComponent indexingComponent;
    //endregion

    //region Constructors
    public IndexingComponentIntegrationTest() {
    }
    //endregion

    //region Test callbacks
    @Before
    public void before() {
        cleanUpClusterIndices();
    }
    //endregion

    //region Test methods
    @Test
    public void testCreateIndexAndSetupMappings() {
        // given
        // when
        final String newIndexWithPreparedMappings = indexingComponent.createIndexAndSetupMappings(ALIAS_NAME, TYPES);
        // then
        assertNotNull(newIndexWithPreparedMappings);
        assertTrue(elasticsearchClientWrapper.indexExists(newIndexWithPreparedMappings));
    }

    @Test
    public void testCreateAliasAndDeleteOldIndices() {
        // given
        final String aliasName = UUID.randomUUID().toString();
        // when
        final String newIndexWithPreparedMappings = indexingComponent.createIndexAndSetupMappings(ALIAS_NAME, TYPES);
        indexingComponent.createAliasAndDeleteOldIndices(aliasName, newIndexWithPreparedMappings);
        // then
        refreshIndex(newIndexWithPreparedMappings);
    }
    //endregion
}
