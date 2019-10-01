package org.biacode.escommons.toolkit.component.impl

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.biacode.escommons.toolkit.test.AbstractEsCommonsToolkitUnitTest
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.easymock.TestSubject
import org.elasticsearch.action.search.SearchAction
import org.elasticsearch.action.search.SearchRequestBuilder
import org.elasticsearch.client.Client
import org.elasticsearch.threadpool.ThreadPool
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 9/5/17
 * Time: 5:07 PM
 */
class ScrollSearchComponentImplTest : AbstractEsCommonsToolkitUnitTest() {

    //region Test subject and mocks
    @TestSubject
    private val scrollSearchComponent = ScrollSearchComponentImpl()

    @Mock
    private lateinit var esClient: Client
    //endregion

    //region Test methods
    @Test
    fun testGetScrollResponse() {
        // test data
        resetAll()
        // expectations
        val threadPool = EasyMock.createMock(ThreadPool::class.java)
        expect(esClient.threadPool()).andReturn(threadPool).anyTimes()
        replayAll()
        // test scenario
        assertThatThrownBy { scrollSearchComponent.scroll(SearchRequestBuilder(esClient, SearchAction.INSTANCE), Person::class.java, -1) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }
    //endregion

}