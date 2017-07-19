package com.sfl.coolmonkey.escommons.api.rest.setup;

import com.sfl.coolmonkey.escommons.api.model.common.EsCommonsResultResponse;
import com.sfl.coolmonkey.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.request.PrepareIndexRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.response.PrepareIndexResponse;
import com.sfl.coolmonkey.escommons.core.component.IndexingComponent;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
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
            esCommonsController.prepareIndex(new PrepareIndexRequest(null, Collections.singletonList(UUID.randomUUID().toString())));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            esCommonsController.prepareIndex(new PrepareIndexRequest(UUID.randomUUID().toString(), null));
            fail();
        } catch (final IllegalArgumentException ignore) {
        }
        verifyAll();
    }

    @Test
    public void testPrepareIndex2() {
        // test data
        resetAll();
        // expectations
        final String newIndexName = UUID.randomUUID().toString();
        final PrepareIndexRequest request = new PrepareIndexRequest(UUID.randomUUID().toString(), Collections.singletonList(UUID.randomUUID().toString()));
        expect(indexingComponent.createIndexAndSetupMappings(request.getAlias(), request.getTypes())).andReturn(newIndexName);
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
        indexingComponent.createAliasAndDeleteOldIndices(request.getAlias(), request.getIndexName());
        replayAll();
        // test scenario
        final ResponseEntity<EsCommonsResultResponse> result = esCommonsController.changeAlias(request);
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
        final ResponseEntity<EsCommonsResultResponse> result = esCommonsController.removeIndexByName(request);
        assertThat(result.getBody()).isNotNull();
        verifyAll();
    }
    //endregion

    //endregion

}