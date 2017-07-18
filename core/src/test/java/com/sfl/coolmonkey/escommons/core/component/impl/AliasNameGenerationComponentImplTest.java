package com.sfl.coolmonkey.escommons.core.component.impl;

import com.sfl.coolmonkey.escommons.core.component.ClockComponent;
import com.sfl.coolmonkey.escommons.core.component.IndexNameGenerationComponent;
import com.sfl.coolmonkey.escommons.core.test.AbstractCoreUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.util.UUID;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
public class AliasNameGenerationComponentImplTest extends AbstractCoreUnitTest {

    //region Test subject and mocks
    @TestSubject
    private IndexNameGenerationComponent indexNameGenerationComponent = new IndexNameGenerationComponentImpl();

    @Mock
    private ClockComponent clockComponent;
    //endregion

    //region Constructors
    public AliasNameGenerationComponentImplTest() {
    }
    //endregion

    //region Test subject and mocks
    @Test
    public void testGenerateNameForGivenIndexWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            indexNameGenerationComponent.generateNameForGivenIndex(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGenerateNameForGivenIndex() {
        // Test data
        final String currentDateString = printCurrentDate();
        final String aliasName = UUID.randomUUID().toString();
        // Reset
        resetAll();
        // Expectations
        expect(clockComponent.printCurrentDateTimeWithPredefinedFormatter()).andReturn(currentDateString);
        // Replay
        replayAll();
        // Run test scenario
        final String result = indexNameGenerationComponent.generateNameForGivenIndex(aliasName);
        // Verify
        verifyAll();
        assertTrue(result.startsWith(aliasName + "_date_" + currentDateString + "_uuid_"));
    }
    //endregion

    //region Utility methods
    private String printCurrentDate() {
        return DateTimeFormat.forPattern("yyyy_MM_dd_HH_mm_ss_SSS").print(DateTime.now());
    }
    //endregion
}