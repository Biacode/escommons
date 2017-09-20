package com.biacode.escommons.api.rest.setup;

import com.biacode.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import com.biacode.escommons.api.model.setup.request.PrepareIndexRequest;
import com.biacode.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import com.biacode.escommons.core.component.IndexingComponent;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * Created by Arthur Asatryan.
 * Date: 7/19/17
 * Time: 12:31 PM
 */
@RunWith(EasyMockRunner.class)
public class EsCommonsResourceTest extends EasyMockSupport {

    //region Test subject and mocks
    @TestSubject
    private EsCommonsResource esCommonsResource = new EsCommonsResource();

    @Mock
    private IndexingComponent indexingComponent;
    //endregion

    //region Test methods

    //region Prepare index

    /**
     * With invalid arguments
     */
    @Test
    public void testPrepareIndex1() {
        // test data
        final HashMap<String, Object> settings = new HashMap<>();
        resetAll();
        // expectations
        replayAll();
        // test scenario
        try {
            esCommonsResource.prepareIndex(null);
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsResource.prepareIndex(new PrepareIndexRequest(null, Collections.singletonList(UUID.randomUUID().toString()), settings));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsResource.prepareIndex(new PrepareIndexRequest(UUID.randomUUID().toString(), null, settings));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        verifyAll();
    }

    @Test
    public void testPrepareIndex2() {
        // test data
        final HashMap<String, Object> settings = new HashMap<>();
        resetAll();
        // expectations
        final String newIndexName = UUID.randomUUID().toString();
        final PrepareIndexRequest request = new PrepareIndexRequest(UUID.randomUUID().toString(), Collections.singletonList(UUID.randomUUID().toString()),settings);
        expect(indexingComponent.createIndexAndSetupMappings(request.getAlias(), request.getTypes(),settings)).andReturn(newIndexName);
        replayAll();
        // test scenario
        final Response result = esCommonsResource.prepareIndex(request);
        assertThat(result.getEntity())
                .extracting("response")
                .extracting("indexName")
                .containsOnly(newIndexName);
        verifyAll();
    }
    //endregion

    //region changeAlias

    /**
     * With invalid arguments
     */
    @Test
    public void testChangeAlias1() {
        // test data
        resetAll();
        // expectations
        replayAll();
        // test scenario
        try {
            esCommonsResource.changeAlias(null);
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsResource.changeAlias(new ChangeIndexAliasRequest(null, UUID.randomUUID().toString()));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsResource.changeAlias(new ChangeIndexAliasRequest(UUID.randomUUID().toString(), null));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        verifyAll();
    }

    @Test
    public void testChangeAlias2() {
        // test data
        resetAll();
        final ChangeIndexAliasRequest request = new ChangeIndexAliasRequest(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        // expectations
        indexingComponent.createAliasAndDeleteOldIndices(request.getAlias(), request.getIndexName());
        replayAll();
        // test scenario
        final Response result = esCommonsResource.changeAlias(request);
        assertThat(result.getEntity()).isNotNull();
        verifyAll();
    }
    //endregion

    //region removeIndexByName

    /**
     * With invalid arguments
     */
    @Test
    public void testRemoveIndexByName1() {
        // test data
        resetAll();
        // expectations
        replayAll();
        // test scenario
        try {
            esCommonsResource.removeIndexByName(null);
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsResource.removeIndexByName(new RemoveIndexByNameRequest(null));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        verifyAll();
    }

    @Test
    public void testRemoveIndexByName2() {
        // test data
        resetAll();
        final RemoveIndexByNameRequest request = new RemoveIndexByNameRequest(UUID.randomUUID().toString());
        // expectations
        indexingComponent.removeIndexByName(request.getIndexName());
        replayAll();
        // test scenario
        final Response result = esCommonsResource.removeIndexByName(request);
        assertThat(result.getEntity()).isNotNull();
        verifyAll();
    }
    //endregion

    //endregion

}