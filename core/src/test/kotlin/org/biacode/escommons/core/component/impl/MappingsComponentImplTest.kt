package org.biacode.escommons.core.component.impl

import org.biacode.escommons.core.component.ResourceReaderComponent
import org.biacode.escommons.core.exception.EsCoreRuntimeException
import org.biacode.escommons.core.test.AbstractCoreUnitTest
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.easymock.TestSubject
import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
class MappingsComponentImplTest : AbstractCoreUnitTest() {

    //region Test subject and mocks
    @TestSubject
    private val mappingsComponent = MappingsComponentImpl()

    @Mock
    private val resourceReaderComponent: ResourceReaderComponent? = null
    //endregion

    //region Test methods

    //region readMappings
    @Test
    fun testReadMappings() {
        // Test data
        val aliasName = UUID.randomUUID().toString()
        val documentType = UUID.randomUUID().toString()
        val path = "mappings/$aliasName/$documentType.json"
        val mappings = "mappings"
        // Reset
        resetAll()
        // Expectations
        expect(resourceReaderComponent!!.asString(path)).andReturn(Optional.of(mappings))
        // Replay
        replayAll()
        // Run test scenario
        val result = mappingsComponent.readMappings(aliasName, documentType)
        // Verify
        verifyAll()
        assertNotNull(result)
        assertEquals(mappings, result)
    }

    @Test
    fun testReadMappingsWhenCanNotReadMappings() {
        // Test data
        val aliasName = UUID.randomUUID().toString()
        val documentType = UUID.randomUUID().toString()
        val path = "mappings/$aliasName/$documentType.json"
        // Reset
        resetAll()
        // Expectations
        expect(resourceReaderComponent!!.asString(path)).andReturn(Optional.empty())
        // Replay
        replayAll()
        // Run test scenario
        try {
            mappingsComponent.readMappings(aliasName, documentType)
            fail()
        } catch (ignore: EsCoreRuntimeException) {
        }
        // Verify
        verifyAll()
    }
    //endregion

    //endregion

}