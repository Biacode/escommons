package com.biacode.escommons.core.component.impl;

import com.biacode.escommons.core.component.ElasticsearchClientWrapper;
import com.biacode.escommons.core.component.IndexNameGenerationComponent;
import com.biacode.escommons.core.component.IndexingComponent;
import com.biacode.escommons.core.component.MappingsComponent;
import com.biacode.escommons.core.test.AbstractCoreUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.*;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
public class IndexingComponentImplTest extends AbstractCoreUnitTest {

    //region Test subject and mocks
    @TestSubject
    private IndexingComponent indexingComponent = new IndexingComponentImpl();

    @Mock
    private MappingsComponent mappingsComponent;

    @Mock
    private IndexNameGenerationComponent indexNameGenerationComponent;

    @Mock
    private ElasticsearchClientWrapper elasticsearchClientWrapper;
    //endregion

    //region Constructors
    public IndexingComponentImplTest() {
    }
    //endregion

    //region Test methods

    //region createIndexAndSetupMappings
    @Test
    public void testCreateIndexAndSetupMappingsWithInvalidArguments() {
        // Test data
        final Map<String, Object> settings = new HashMap<>();
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            indexingComponent.createIndexAndSetupMappings(null, Collections.singletonList(UUID.randomUUID().toString()), settings);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            indexingComponent.createIndexAndSetupMappings(UUID.randomUUID().toString(), null, settings);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateIndexAndSetupMappingsWithoutSettings() {
        // Test data
        final String newIndexName = UUID.randomUUID().toString();
        final String originalIndex = UUID.randomUUID().toString();
        final String type = UUID.randomUUID().toString();
        final List<String> types = new ArrayList<>();
        final String mappings = "Some dummy mappings";
        types.add(type);
        // Reset
        resetAll();
        // Expectations
        expect(indexNameGenerationComponent.generateNameForGivenIndex(originalIndex)).andReturn(newIndexName);
        expect(elasticsearchClientWrapper.createIndex(newIndexName)).andReturn(true);
        expect(mappingsComponent.readMappings(originalIndex, type)).andReturn(mappings);
        expect(elasticsearchClientWrapper.putMapping(newIndexName, type, mappings)).andReturn(true);
        elasticsearchClientWrapper.refreshIndex(newIndexName);
        // Replay
        replayAll();
        // Run test scenario
        indexingComponent.createIndexAndSetupMappings(originalIndex, types, null);
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateIndexAndSetupMappingsWithSettings() {
        // Test data
        final String newIndexName = UUID.randomUUID().toString();
        final String originalIndex = UUID.randomUUID().toString();
        final String type = UUID.randomUUID().toString();
        final List<String> types = new ArrayList<>();
        final String mappings = "Some dummy mappings";
        final Map<String, Object> settings = new HashMap<>();
        types.add(type);
        // Reset
        resetAll();
        // Expectations
        expect(indexNameGenerationComponent.generateNameForGivenIndex(originalIndex)).andReturn(newIndexName);
        expect(elasticsearchClientWrapper.createIndex(newIndexName, settings)).andReturn(true);
        expect(mappingsComponent.readMappings(originalIndex, type)).andReturn(mappings);
        expect(elasticsearchClientWrapper.putMapping(newIndexName, type, mappings)).andReturn(true);
        elasticsearchClientWrapper.refreshIndex(newIndexName);
        // Replay
        replayAll();
        // Run test scenario
        indexingComponent.createIndexAndSetupMappings(originalIndex, types, settings);
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
            indexingComponent.addAlias(null, UUID.randomUUID().toString());
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            indexingComponent.addAlias(UUID.randomUUID().toString(), null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAddAlias() {
        // Test data
        final String newIndex = "biacode_" + UUID.randomUUID().toString();
        final String originalIndex = "biacode";
        // Reset
        resetAll();
        // Expectations
        expect(elasticsearchClientWrapper.addAlias(newIndex, originalIndex)).andReturn(true);
        // Replay
        replayAll();
        // Run test scenario
        indexingComponent.addAlias(originalIndex, newIndex);
        // Verify
        verifyAll();
    }
    //endregion

    //region removeIndexByName
    @Test
    public void testRemoveIndexByNameWithInvalidArguments() {
        resetAll();
        // test data
        // expectations
        replayAll();
        try {
            indexingComponent.removeIndexByName(null);
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        verifyAll();
    }

    @Test
    public void testRemoveIndexByName() {
        resetAll();
        // test data
        final String indexName = "new_index";
        final Set<String> indices = new HashSet<>();
        indices.add("new_index_1");
        indices.add("new_index_2");
        indices.add("new_index");
        // expectations
        expect(elasticsearchClientWrapper.getClusterIndices()).andReturn(indices);
        expect(elasticsearchClientWrapper.deleteIndex("new_index")).andReturn(true);
        replayAll();
        indexingComponent.removeIndexByName(indexName);
        verifyAll();
    }
    //endregion

    //endregion

}