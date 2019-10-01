package org.biacode.escommons.toolkit.component.impl

import org.biacode.escommons.core.model.document.AbstractEsDocument
import org.biacode.escommons.toolkit.component.JsonComponent
import org.biacode.escommons.toolkit.test.AbstractEsCommonsToolkitUnitTest
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.easymock.TestSubject
import org.elasticsearch.action.get.GetResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
class SearchResponseComponentImplTest : AbstractEsCommonsToolkitUnitTest() {

    //region Test subject and mocks
    @TestSubject
    private val searchResponseComponent = SearchResponseComponentImpl()

    @Mock
    private lateinit var jsonComponent: JsonComponent
    //endregion

    //region Test methods

    //region convertGetResponseToDocument
    @Test
    fun testConvertGetResponseToDocument() {
        // Test data
        val getResponse = createMock(GetResponse::class.java)
        val clazz = AbstractEsDocument::class.java
        val source = "source"
        val id = "id"
        val document = Person(UUID.randomUUID().toString())
        // Reset
        resetAll()
        // Expectations
        expect(getResponse.sourceAsString).andReturn(source)
        expect(jsonComponent.deserializeFromString(source, clazz)).andReturn(document)
        expect(getResponse.id).andReturn(id)
        // Replay
        replayAll()
        // Run test scenario
        val response = searchResponseComponent.document(getResponse, clazz)
        // Verify
        verifyAll()
        assertNotNull(response)
        assertEquals(document, response)
        assertEquals(id, response.id)
    }
    //endregion

    //endregion

}