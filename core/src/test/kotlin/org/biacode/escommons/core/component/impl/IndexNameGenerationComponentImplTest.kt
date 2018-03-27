package org.biacode.escommons.core.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.biacode.escommons.core.component.ClockComponent
import org.biacode.escommons.core.test.AbstractCoreUnitTest
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.easymock.TestSubject
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
class IndexNameGenerationComponentImplTest : AbstractCoreUnitTest() {

    //region Test subject and mocks
    @TestSubject
    private val indexNameGenerationComponent = IndexNameGenerationComponentImpl()

    @Mock
    private lateinit var clockComponent: ClockComponent
    //endregion

    //region Test methods
    @Test
    fun testGenerateNameForGivenIndex() {
        // Test data
        val aliasName = UUID.randomUUID().toString()
        val currentDateString = UUID.randomUUID().toString()
        // Reset
        resetAll()
        // Expectations
        expect(clockComponent.printCurrentDateTimeWithPredefinedFormatter()).andReturn(currentDateString)
        // Replay
        replayAll()
        // Run test scenario
        assertThat(indexNameGenerationComponent.generate(aliasName)).isNotBlank().startsWith(aliasName + "_date_" + currentDateString + "_uuid_")
        // Verify
        verifyAll()
    }
    //endregion
}