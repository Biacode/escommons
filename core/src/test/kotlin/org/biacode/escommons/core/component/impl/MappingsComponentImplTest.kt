package org.biacode.escommons.core.component.impl

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.biacode.escommons.core.component.ResourceReaderComponent
import org.biacode.escommons.core.exception.EsCommonsCoreRuntimeException
import org.biacode.escommons.core.test.AbstractCoreUnitTest
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.easymock.TestSubject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
    private lateinit var resourceReaderComponent: ResourceReaderComponent
    //endregion

    //region Test methods

    //region readMappings
    @Test
    fun testReadMappings() {
        // Test data
        val mappingName = UUID.randomUUID().toString()
        val path = "mappings/$mappingName.json"
        val mappings = UUID.randomUUID().toString()
        // Reset
        resetAll()
        // Expectations
        expect(resourceReaderComponent.asString(path)).andReturn(Optional.of(mappings))
        // Replay
        replayAll()
        // Run test scenario
        val result = mappingsComponent.readMappings(mappingName)
        // Verify
        verifyAll()
        assertNotNull(result)
        assertEquals(mappings, result)
    }

    @Test
    fun testReadMappingsWhenCanNotReadMappings() {
        // Test data
        val mappingName = UUID.randomUUID().toString()
        val path = "mappings/$mappingName.json"
        // Reset
        resetAll()
        // Expectations
        expect(resourceReaderComponent.asString(path)).andReturn(Optional.empty())
        // Replay
        replayAll()
        // Run test scenario
        assertThatThrownBy { mappingsComponent.readMappings(mappingName) }
                .isExactlyInstanceOf(EsCommonsCoreRuntimeException::class.java)
        // Verify
        verifyAll()
    }
    //endregion

    //endregion

}