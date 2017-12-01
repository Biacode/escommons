package org.biacode.escommons.core.component.impl;

import org.biacode.escommons.core.component.ElasticsearchClientWrapper;
import org.biacode.escommons.core.test.AbstractCoreUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequestBuilder;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
public class ElasticsearchClientWrapperImplTest extends AbstractCoreUnitTest {

    //region Test subject and mocks
    @TestSubject
    private ElasticsearchClientWrapper elasticsearchClientWrapper = new ElasticsearchClientWrapperImpl();

    @Mock
    private Client esClient;
    //endregion

    //region Constructors
    public ElasticsearchClientWrapperImplTest() {
    }
    //endregion

    //region Test methods

    //region getClusterIndices
    @Test
    public void testGetClusterIndices() {
        // Covered with integration tests
    }
    //endregion

    //region createIndex
    @Test
    public void testCreateIndexWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            elasticsearchClientWrapper.createIndex(null, Collections.emptyMap());
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }


    @Test
    public void testCreateIndexWithoutSettings() {
        // Test data
        final String indexName = "Index_Name";
        final AdminClient adminClient = createMock(AdminClient.class);
        final IndicesAdminClient indicesAdminClient = createMock(IndicesAdminClient.class);
        final CreateIndexRequestBuilder createIndexRequestBuilder = createMock(CreateIndexRequestBuilder.class);
        final CreateIndexResponse createIndexResponse = createMock(CreateIndexResponse.class);
        // Reset
        resetAll();
        // Expectations
        expect(esClient.admin()).andReturn(adminClient);
        expect(adminClient.indices()).andReturn(indicesAdminClient);
        expect(indicesAdminClient.prepareCreate(indexName)).andReturn(createIndexRequestBuilder);
        expect(createIndexRequestBuilder.get()).andReturn(createIndexResponse);
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = elasticsearchClientWrapper.createIndex(indexName);
        // Verify
        verifyAll();
        assertEquals(createIndexResponse.isAcknowledged(), result);
    }

    @Test
    public void testCreateIndex() {
        // Test data
        final String indexName = "Index_Name";
        final Map<String, Object> settings = Collections.singletonMap("settings_key", "settings_value");
        final AdminClient adminClient = createMock(AdminClient.class);
        final IndicesAdminClient indicesAdminClient = createMock(IndicesAdminClient.class);
        final CreateIndexRequestBuilder createIndexRequestBuilder = createMock(CreateIndexRequestBuilder.class);
        final CreateIndexResponse createIndexResponse = createMock(CreateIndexResponse.class);
        // Reset
        resetAll();
        // Expectations
        expect(esClient.admin()).andReturn(adminClient);
        expect(adminClient.indices()).andReturn(indicesAdminClient);
        expect(indicesAdminClient.prepareCreate(indexName)).andReturn(createIndexRequestBuilder);
        expect(createIndexRequestBuilder.setSettings(settings)).andReturn(createIndexRequestBuilder);
        expect(createIndexRequestBuilder.get()).andReturn(createIndexResponse);
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = elasticsearchClientWrapper.createIndex(indexName, settings);
        // Verify
        verifyAll();
        assertEquals(createIndexResponse.isAcknowledged(), result);
    }
    //endregion

    //region putMapping
    @Test
    public void testPutMappingWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            elasticsearchClientWrapper.putMapping(null, "docType", "mappings");
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            elasticsearchClientWrapper.putMapping("index", null, "mappings");
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            elasticsearchClientWrapper.putMapping("index", "docType", null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testPutMapping() {
        // Test data
        final String indexName = "Index_Name";
        final String documentType = "documentType";
        final String mappings = "mappings";
        final AdminClient adminClient = createMock(AdminClient.class);
        final IndicesAdminClient indicesAdminClient = createMock(IndicesAdminClient.class);
        final PutMappingRequestBuilder putMappingRequestBuilder = createMock(PutMappingRequestBuilder.class);
        final PutMappingResponse putMappingResponse = createMock(PutMappingResponse.class);
        // Reset
        resetAll();
        // Expectations
        expect(esClient.admin()).andReturn(adminClient);
        expect(adminClient.indices()).andReturn(indicesAdminClient);
        expect(indicesAdminClient.preparePutMapping(indexName)).andReturn(putMappingRequestBuilder);
        expect(putMappingRequestBuilder.setType(documentType)).andReturn(putMappingRequestBuilder);
        expect(putMappingRequestBuilder.setSource(mappings)).andReturn(putMappingRequestBuilder);
        expect(putMappingRequestBuilder.get()).andReturn(putMappingResponse);
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = elasticsearchClientWrapper.putMapping(indexName, documentType, mappings);
        // Verify
        verifyAll();
        assertEquals(putMappingResponse.isAcknowledged(), result);
    }
    //endregion

    //region refreshIndex
    @Test
    public void testRefreshIndexWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            elasticsearchClientWrapper.refreshIndex(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testRefreshIndex() {
        // Test data
        final String indexName = "Index_Name";
        final AdminClient adminClient = createMock(AdminClient.class);
        final IndicesAdminClient indicesAdminClient = createMock(IndicesAdminClient.class);
        final RefreshRequestBuilder refreshRequestBuilder = createMock(RefreshRequestBuilder.class);
        final PutMappingResponse putMappingResponse = createMock(PutMappingResponse.class);
        // Reset
        resetAll();
        // Expectations
        expect(esClient.admin()).andReturn(adminClient);
        expect(adminClient.indices()).andReturn(indicesAdminClient);
        expect(indicesAdminClient.prepareRefresh(indexName)).andReturn(refreshRequestBuilder);
        expect(refreshRequestBuilder.get()).andReturn(createMock(RefreshResponse.class));
        // Replay
        replayAll();
        // Run test scenario
        elasticsearchClientWrapper.refreshIndex(indexName);
        // Verify
        verifyAll();
    }
    //endregion

    //region addAlias
    @Test
    public void testAddAliasWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            elasticsearchClientWrapper.addAlias(null, "alias");
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            elasticsearchClientWrapper.addAlias("index", null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAddAlias() {
        //Test data
        final String indexName = "index";
        final String aliasName = "alias";
        final AdminClient adminClient = createMock(AdminClient.class);
        final IndicesAdminClient indicesAdminClient = createMock(IndicesAdminClient.class);
        final IndicesAliasesRequestBuilder indicesAliasesRequestBuilder = createMock(IndicesAliasesRequestBuilder.class);
        final IndicesAliasesResponse indicesAliasesResponse = createMock(IndicesAliasesResponse.class);
        //Reset
        resetAll();
        //Expectations
        expect(esClient.admin()).andReturn(adminClient);
        expect(adminClient.indices()).andReturn(indicesAdminClient);
        expect(indicesAdminClient.prepareAliases()).andReturn(indicesAliasesRequestBuilder);
        expect(indicesAliasesRequestBuilder.addAlias(indexName, aliasName)).andReturn(indicesAliasesRequestBuilder);
        expect(indicesAliasesRequestBuilder.get()).andReturn(indicesAliasesResponse);
        //Replay
        replayAll();
        //Run test scenario
        final boolean result = elasticsearchClientWrapper.addAlias(indexName, aliasName);
        //Verify
        verifyAll();
        assertEquals(indicesAliasesResponse.isAcknowledged(), result);
    }
    //endregion

    //region deleteIndex
    @Test
    public void testDeleteIndexWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            elasticsearchClientWrapper.deleteIndex(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testDeleteIndex() {
        //Test data
        final String indexName = "index";
        final AdminClient adminClient = createMock(AdminClient.class);
        final IndicesAdminClient indicesAdminClient = createMock(IndicesAdminClient.class);
        final DeleteIndexRequestBuilder deleteIndexRequestBuilder = createMock(DeleteIndexRequestBuilder.class);
        final DeleteIndexResponse deleteIndexResponse = createMock(DeleteIndexResponse.class);
        //Reset
        resetAll();
        //Expectations
        expect(esClient.admin()).andReturn(adminClient);
        expect(adminClient.indices()).andReturn(indicesAdminClient);
        expect(indicesAdminClient.prepareDelete(indexName)).andReturn(deleteIndexRequestBuilder);
        expect(deleteIndexRequestBuilder.get()).andReturn(deleteIndexResponse);
        //Replay
        replayAll();
        //Run test scenario
        final boolean result = elasticsearchClientWrapper.deleteIndex(indexName);
        //Verify
        verifyAll();
        assertEquals(deleteIndexResponse.isAcknowledged(), result);
    }
    //endregion

    //region indexExists
    @Test
    public void testIndexExistsWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            elasticsearchClientWrapper.indexExists(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testIndexExists() {
        //Test data
        final String indexName = "index";
        final AdminClient adminClient = createMock(AdminClient.class);
        final IndicesAdminClient indicesAdminClient = createMock(IndicesAdminClient.class);
        final IndicesExistsRequestBuilder indicesExistsRequestBuilder = createMock(IndicesExistsRequestBuilder.class);
        final IndicesExistsResponse indicesExistsResponse = createMock(IndicesExistsResponse.class);
        final boolean exists = new Random().nextBoolean();
        //Reset
        resetAll();
        //Expectations
        expect(esClient.admin()).andReturn(adminClient);
        expect(adminClient.indices()).andReturn(indicesAdminClient);
        expect(indicesAdminClient.prepareExists(indexName)).andReturn(indicesExistsRequestBuilder);
        expect(indicesExistsRequestBuilder.get()).andReturn(indicesExistsResponse);
        expect(indicesExistsResponse.isExists()).andReturn(exists);
        //Replay
        replayAll();
        //Run test scenario
        final boolean result = elasticsearchClientWrapper.indexExists(indexName);
        //Verify
        verifyAll();
        assertEquals(exists, result);
    }
    //endregion

    //endregion
}