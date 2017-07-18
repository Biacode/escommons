package com.sfl.coolmonkey.escommons.core.impl;

import com.sfl.coolmonkey.escommons.core.component.JsonComponent;
import com.sfl.coolmonkey.escommons.core.component.impl.JsonComponentImpl;
import com.sfl.coolmonkey.escommons.core.exception.EsCoreRuntimeException;
import com.sfl.coolmonkey.escommons.core.test.AbstractCoreUnitTest;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
public class JsonComponentImplTest extends AbstractCoreUnitTest {

    //region Test subject and mocks
    @TestSubject
    private JsonComponent jsonComponent = new JsonComponentImpl();
    //endregion

    //region Constructors
    public JsonComponentImplTest() {
    }
    //endregion

    //region Test methods

    //region deserialize
    @Test
    public void testParseWithInvalidArguments() {
        // Test data
        final String validSource = "validSource";
        final Class<Object> validClass = Object.class;
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            jsonComponent.deserialize(null, validClass);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            jsonComponent.deserialize(validSource, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testParse() {
        // Test data
        final String firstName = "operator uuid";
        final String source = "{\"firstName\": \"" + firstName + "\"}";
        final Class<Person> clazz = Person.class;
        final Person expectedScriptDocument = new Person();
        expectedScriptDocument.setFirstName(firstName);
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        final Person response = jsonComponent.deserialize(source, clazz);
        // Verify
        verifyAll();
        assertNotNull(response);
        assertEquals(expectedScriptDocument, response);
        assertEquals(firstName, response.getFirstName());
    }

    @Test
    public void testParseInvalidJson() {
        // Test data
        final String source = "#&$@*ThisIsNotValidJson&$#(@*#*(&";
        final Class<Person> clazz = Person.class;
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            jsonComponent.deserialize(source, clazz);
            fail("Exception should be thrown");
        } catch (final EsCoreRuntimeException ignore) {
        }
        // Verify
        verifyAll();
    }
    //endregion

    //region serialize
    @Test
    public void testSerializeWithInvalidArguments() {
        // Test data
        final String validSource = "validSource";
        final Class<Object> validClass = Object.class;
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            jsonComponent.serialize(null, validClass);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            jsonComponent.serialize(validSource, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testSerialize() {
        // Test data
        final String name = "test name";
        final Map<String, String> source = Collections.singletonMap("name", name);
        final String expected = "{\"name\":\"" + name + "\"}";
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        final String result = jsonComponent.serialize(source, Map.class);
        // Verify
        verifyAll();
        assertNotNull(result);
        assertEquals(expected, result);
    }

    //endregion

    //endregion

}