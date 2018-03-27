package org.biacode.escommons.core.component;

import org.biacode.escommons.core.test.AbstractIntegrationTest;
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
 * Created by Arthur Asatryan.
 * Date: 7/23/17
 * Time: 9:10 PM
 */
@Component
public class ElasticsearchClientWrapperIntegrationTest extends AbstractIntegrationTest {

    //region Dependencies
    @Autowired
    private ElasticsearchClientWrapper elasticsearchClientWrapper;
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
        // when
        final Set<String> clusterIndices = elasticsearchClientWrapper.clusterIndices();
        // then
        assertNotNull(clusterIndices);
        assertTrue(expectedIndices.stream().allMatch(clusterIndices::contains));
    }
    //endregion
}
