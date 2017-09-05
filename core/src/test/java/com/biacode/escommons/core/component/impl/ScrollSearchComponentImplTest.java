package com.biacode.escommons.core.component.impl;

import com.biacode.escommons.core.component.ScrollSearchComponent;
import com.biacode.escommons.core.test.AbstractCoreUnitTest;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.threadpool.ThreadPool;
import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * Created by Arthur Asatryan.
 * Date: 9/5/17
 * Time: 5:07 PM
 */
public class ScrollSearchComponentImplTest extends AbstractCoreUnitTest {


    //region Test subject and mocks
    @TestSubject
    private ScrollSearchComponent scrollSearchComponent = new ScrollSearchComponentImpl();

    @Mock
    private Client esClient;
    //endregion

    //region Constructors
    public ScrollSearchComponentImplTest() {
    }
    //endregion

    //region Test methods

    /**
     * With invalid args
     */
    @Test
    public void testGetScrollResponse() {
        // test data
        resetAll();
        // expectations
        final ThreadPool threadPool = EasyMock.createMock(ThreadPool.class);
        expect(esClient.threadPool()).andReturn(threadPool).anyTimes();
        replayAll();
        // test scenario
        try {
            scrollSearchComponent.getScrollResponse(null, Person.class, 60_000);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            scrollSearchComponent.getScrollResponse(new SearchRequestBuilder(esClient, SearchAction.INSTANCE), null, 60_000);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            scrollSearchComponent.getScrollResponse(new SearchRequestBuilder(esClient, SearchAction.INSTANCE), Person.class, -1);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        verifyAll();
    }
    //endregion

}