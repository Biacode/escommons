package org.biacode.escommons.toolkit.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.biacode.escommons.toolkit.test.AbstractEsCommonsToolkitUnitTest
import org.easymock.TestSubject
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 3/27/18
 * Time: 5:30 PM
 */
class ClockComponentImplTest : AbstractEsCommonsToolkitUnitTest() {

    //region Test subject and mocks
    @TestSubject
    private val clockComponent = ClockComponentImpl()
    //endregion

    //region Test methods
    @Test
    fun `test printCurrentDateTimeWithPredefinedFormatter`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThat(clockComponent.printCurrentDateTimeWithPredefinedFormatter()).isNotNull().isNotBlank()
        verifyAll()
    }
    //endregion

}