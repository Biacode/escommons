package org.biacode.escommons.core.test;

import org.biacode.escommons.core.component.ElasticsearchClientWrapper;
import org.biacode.escommons.core.component.MappingsComponent;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    protected void refreshIndex(final String indexName) {
        elasticsearchClientWrapper.refreshIndex(indexName);
    }
    //endregion

    //region Utility methods
    private void prepareTestIndexes() {
        cleanUpClusterIndices();
    }

    protected void cleanUpClusterIndices() {
        elasticsearchClientWrapper.clusterIndices().forEach(elasticsearchClientWrapper::deleteIndex);
    }
    //endregion
}
