package com.sfl.coolmonkey.escommons.core.component;

import com.sfl.coolmonkey.escommons.core.test.AbstractIntegrationTest;
import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: Arthur Asatryan
 * Company: SFL LLC
 * Date: 3/1/16
 * Time: 10:58 AM
 */
@Component
public class ElasticsearchClientWrapperIntegrationTest extends AbstractIntegrationTest {

    //region Dependencies
    @Autowired
    private ElasticsearchClientWrapper elasticsearchClientWrapper;

    @Autowired
    private Client client;
    //endregion

    //region Constructors
    public ElasticsearchClientWrapperIntegrationTest() {
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
    public void testGetClusterIndices() {
        // given
        final Set<String> expectedIndices = new HashSet<>();
        expectedIndices.add("address_" + UUID.randomUUID().toString());
        expectedIndices.add("address_" + UUID.randomUUID().toString());
        expectedIndices.add("address_" + UUID.randomUUID().toString());
        expectedIndices.forEach(s -> client.admin().indices().prepareCreate(s).get());
        // when
        final Set<String> clusterIndices = elasticsearchClientWrapper.getClusterIndices();
        // then
        assertNotNull(clusterIndices);
        assertTrue(expectedIndices.stream().allMatch(clusterIndices::contains));
    }
    //endregion
}
