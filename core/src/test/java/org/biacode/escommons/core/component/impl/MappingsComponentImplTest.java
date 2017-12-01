package org.biacode.escommons.core.component.impl;

import org.biacode.escommons.core.component.ClasspathResourceReaderComponent;
import org.biacode.escommons.core.component.MappingsComponent;
import org.biacode.escommons.core.exception.EsCoreRuntimeException;
import org.biacode.escommons.core.test.AbstractCoreUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
public class MappingsComponentImplTest extends AbstractCoreUnitTest {

    //region Test subject and mocks
    @TestSubject
    private MappingsComponent mappingsComponent = new MappingsComponentImpl();

    @Mock
    private ClasspathResourceReaderComponent classpathResourceReaderComponent;
    //endregion

    //region Constructors
    public MappingsComponentImplTest() {
    }
    //endregion

    //region Test methods

    //region readMappings
    @Test
    public void testReadMappingsWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            mappingsComponent.readMappings(null, UUID.randomUUID().toString());
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            mappingsComponent.readMappings(UUID.randomUUID().toString(), null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testReadMappings() {
        // Test data
        final String aliasName = UUID.randomUUID().toString();
        final String documentType = UUID.randomUUID().toString();
        final String path = "mappings/" + aliasName + "/" + documentType + ".json";
        final String mappings = "mappings";
        // Reset
        resetAll();
        // Expectations
        expect(classpathResourceReaderComponent.readFileFromClasspath(path)).andReturn(Optional.of(mappings));
        // Replay
        replayAll();
        // Run test scenario
        final String result = mappingsComponent.readMappings(aliasName, documentType);
        // Verify
        verifyAll();
        assertNotNull(result);
        assertEquals(mappings, result);
    }

    @Test
    public void testReadMappingsWhenCanNotReadMappings() {
        // Test data
        final String aliasName = UUID.randomUUID().toString();
        final String documentType = UUID.randomUUID().toString();
        final String path = "mappings/" + aliasName + "/" + documentType + ".json";
        // Reset
        resetAll();
        // Expectations
        expect(classpathResourceReaderComponent.readFileFromClasspath(path)).andReturn(Optional.empty());
        // Replay
        replayAll();
        // Run test scenario
        try {
            mappingsComponent.readMappings(aliasName, documentType);
            fail();
        } catch (final EsCoreRuntimeException ignore) {
        }
        // Verify
        verifyAll();
    }
    //endregion

    //endregion

}