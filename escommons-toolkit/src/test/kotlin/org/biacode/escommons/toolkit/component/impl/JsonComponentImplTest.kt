package org.biacode.escommons.toolkit.component.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.biacode.escommons.toolkit.test.AbstractEsCommonsToolkitUnitTest
import org.easymock.TestSubject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
class JsonComponentImplTest : AbstractEsCommonsToolkitUnitTest() {

    //region Test subject and mocks
    @TestSubject
    private val jsonComponent = JsonComponentImpl()
    //endregion

    //region Test callbacks
    @Before
    fun beforeThisTest() {
        jsonComponent.objectMapper = ObjectMapper()
    }
    //endregion

    //region Test methods

    //region deserializeFromString
    @Test
    fun testParse() {
        // Test data
        val firstName = UUID.randomUUID().toString()
        val source = "{\"firstName\": \"$firstName\"}"
        val clazz = Person::class.java
        val expectedScriptDocument = Person(firstName)
        // Reset
        resetAll()
        // Expectations
        // Replay
        replayAll()
        // Run test scenario
        val response = jsonComponent.deserializeFromString(source, clazz)
        // Verify
        verifyAll()
        assertNotNull(response)
        assertEquals(expectedScriptDocument, response)
        assertEquals(firstName, response.firstName)
    }
    //endregion

    //region serialize
    @Test
    fun testSerialize() {
        // Test data
        val name = "test name"
        val source = Collections.singletonMap("name", name)
        val expected = "{\"name\":\"$name\"}"
        // Reset
        resetAll()
        // Expectations
        // Replay
        replayAll()
        // Run test scenario
        val result = jsonComponent.serialize(source)
        // Verify
        verifyAll()
        assertNotNull(result)
        assertEquals(expected, result)
    }
    //endregion

    //endregion

}