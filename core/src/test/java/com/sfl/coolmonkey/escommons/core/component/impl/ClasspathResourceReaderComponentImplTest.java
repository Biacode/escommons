package com.sfl.coolmonkey.escommons.core.component.impl;

import com.sfl.coolmonkey.escommons.core.component.ClasspathResourceReaderComponent;
import com.sfl.coolmonkey.escommons.core.test.AbstractCoreUnitTest;
import org.apache.commons.lang3.StringUtils;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
public class ClasspathResourceReaderComponentImplTest extends AbstractCoreUnitTest {

    //region Test subject and mocks
    @TestSubject
    private ClasspathResourceReaderComponent classpathResourceReaderComponent = new ClasspathResourceReaderComponentImpl();
    //endregion

    //region Constructors
    public ClasspathResourceReaderComponentImplTest() {
    }
    //endregion

    //region Test methods

    //region readFileFromClasspath
    @Test
    public void testReadFileFromClasspathWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            classpathResourceReaderComponent.readFileFromClasspath(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testReadFileFromClasspath() {
        // Test data
        final String path = "escommons.properties";
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        final Optional<String> result = classpathResourceReaderComponent.readFileFromClasspath(path);
        // Verify
        verifyAll();
        assertTrue(result.isPresent());
        assertFalse(StringUtils.isBlank(result.get()));
    }

    @Test
    public void testReadFileFromClasspathWhenFileDoesNotExist() {
        // Test data
        final String path = "ThisFileDoesNotExistInClassPath";
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        final Optional<String> result = classpathResourceReaderComponent.readFileFromClasspath(path);
        assertFalse(result.isPresent());
        // Verify
        verifyAll();
    }
    //endregion

    //endregion
}