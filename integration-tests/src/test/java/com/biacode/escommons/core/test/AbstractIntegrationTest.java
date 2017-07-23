package com.biacode.escommons.core.test;

import com.biacode.escommons.core.component.ElasticsearchClientWrapper;
import com.biacode.escommons.core.component.MappingsComponent;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 1:16 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-escommons-core-test.xml")
public abstract class AbstractIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    //region Constants
    protected static final String ALIAS_NAME = "escommons";

    protected static final List<String> TYPES = Collections.singletonList("person");
    //endregion

    //region Dependencies
    @Autowired
    private ElasticsearchClientWrapper elasticsearchClientWrapper;

    @Autowired
    private MappingsComponent mappingsComponent;
    //endregion

    //region Constructors
    public AbstractIntegrationTest() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Test callbacks
    @Before
    public void before() {
        prepareTestIndexes();
    }
    //endregion

    //region Protected methods
    protected void refreshIndex() {
        elasticsearchClientWrapper.refreshIndex(ALIAS_NAME);
    }

    protected void refreshIndex(final String indexName) {
        elasticsearchClientWrapper.refreshIndex(indexName);
    }
    //endregion

    //region Utility methods
    private void prepareTestIndexes() {
        cleanUpClusterIndices();
        createIndexAndDeleteIfExists(ALIAS_NAME);
        for (final String documentType : TYPES) {
            elasticsearchClientWrapper.putMapping(ALIAS_NAME, documentType, mappingsComponent.readMappings(ALIAS_NAME, documentType));
        }
    }

    protected void cleanUpClusterIndices() {
        elasticsearchClientWrapper.getClusterIndices().forEach(elasticsearchClientWrapper::deleteIndex);
    }

    private void createIndexAndDeleteIfExists(final String indexName) {
        if (elasticsearchClientWrapper.indexExists(indexName)) {
            elasticsearchClientWrapper.deleteIndex(indexName);
        } else {
            elasticsearchClientWrapper.createIndex(indexName);
        }
    }
    //endregion
}
