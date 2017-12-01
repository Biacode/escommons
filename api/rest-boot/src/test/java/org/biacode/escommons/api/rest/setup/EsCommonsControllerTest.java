package org.biacode.escommons.api.rest.setup;

import org.biacode.escommons.api.model.common.EsCommonsResultResponse;
import org.biacode.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import org.biacode.escommons.api.model.setup.request.PrepareIndexRequest;
import org.biacode.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import org.biacode.escommons.api.model.setup.response.ChangeIndexAliasResponse;
import org.biacode.escommons.api.model.setup.response.PrepareIndexResponse;
import org.biacode.escommons.api.model.setup.response.RemoveIndexByNameResponse;
import org.biacode.escommons.core.component.IndexingComponent;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 6:47 PM
 */
@RunWith(EasyMockRunner.class)
public class EsCommonsControllerTest extends EasyMockSupport {

    //region Test subject and mocks
    @TestSubject
    private EsCommonsController esCommonsController = new EsCommonsController();

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
        final Map<String, Object> settings = new HashMap<>();

        resetAll();
        // expectations
        replayAll();
        // test scenario
        try {
            esCommonsController.prepareIndex(null);
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsController.prepareIndex(new PrepareIndexRequest(null, Collections.singletonList(UUID.randomUUID().toString()), settings));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsController.prepareIndex(new PrepareIndexRequest(UUID.randomUUID().toString(), null, settings));
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
        final PrepareIndexRequest request = new PrepareIndexRequest(UUID.randomUUID().toString(), Collections.singletonList(UUID.randomUUID().toString()), settings);
        expect(indexingComponent.createIndexAndSetupMappings(request.getAlias(), request.getTypes(), settings)).andReturn(newIndexName);
        replayAll();
        // test scenario
        final ResponseEntity<EsCommonsResultResponse<PrepareIndexResponse>> result = esCommonsController.prepareIndex(request);
        assertThat(result.getBody())
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
            esCommonsController.changeAlias(null);
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsController.changeAlias(new ChangeIndexAliasRequest(null, UUID.randomUUID().toString()));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsController.changeAlias(new ChangeIndexAliasRequest(UUID.randomUUID().toString(), null));
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
        indexingComponent.addAlias(request.getAlias(), request.getIndexName());
        replayAll();
        // test scenario
        final ResponseEntity<EsCommonsResultResponse<ChangeIndexAliasResponse>> result = esCommonsController.changeAlias(request);
        assertThat(result.getBody()).isNotNull();
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
            esCommonsController.removeIndexByName(null);
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsController.removeIndexByName(new RemoveIndexByNameRequest(null));
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
        final ResponseEntity<EsCommonsResultResponse<RemoveIndexByNameResponse>> result = esCommonsController.removeIndexByName(request);
        assertThat(result.getBody()).isNotNull();
        verifyAll();
    }
    //endregion

    //endregion

}