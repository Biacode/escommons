package org.biacode.escommons.core.component.impl;

import org.biacode.escommons.core.component.ClockComponent;
import org.biacode.escommons.core.test.AbstractCoreUnitTest;
import org.easymock.TestSubject;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
public class ClockComponentImplTest extends AbstractCoreUnitTest {

    //region Test subject and mocks
    @TestSubject
    private ClockComponent clockComponent = new ClockComponentImpl();
    //endregion

    //region Constructors
    public ClockComponentImplTest() {
    }
    //endregion

    //region Test methods
    @Test
    public void testPrintCurrentDateTimeWithPredefinedFormatter() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        final String currentDateTime = clockComponent.printCurrentDateTimeWithPredefinedFormatter();
        assertNotNull(currentDateTime);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCurrentDateTime() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        final DateTime currentDateTime = clockComponent.getCurrentDateTime();
        assertNotNull(currentDateTime);
        // Verify
        verifyAll();
    }
    //endregion
}