package org.biacode.escommons.toolkit.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.biacode.escommons.toolkit.test.AbstractCoreUnitTest
import org.easymock.TestSubject
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 3/27/18
 * Time: 5:13 PM
 */
class ResourceReaderComponentImplTest : AbstractCoreUnitTest() {

    //region Test subject and mocks
    @TestSubject
    private val resourceReaderComponent = ResourceReaderComponentImpl()
    //endregion

    //region Test methods
    @Test
    fun `test asString when resource with path does not exists`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThat(resourceReaderComponent.asString(UUID.randomUUID().toString()).isPresent).isFalse()
        verifyAll()
    }

    @Test
    fun `test asString`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThat(resourceReaderComponent.asString("escommons.properties").isPresent).isTrue()
        assertThat(resourceReaderComponent.asString("escommons.properties").get()).isNotBlank()
        verifyAll()
    }
    //endregion

}