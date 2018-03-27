package org.biacode.escommons.core.component.impl

import org.biacode.escommons.core.test.AbstractCoreUnitTest
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
class JsonComponentImplTest : AbstractCoreUnitTest() {

    //region Test subject and mocks
    @TestSubject
    private val jsonComponent = JsonComponentImpl()
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
        val result = jsonComponent.serialize(source, Map::class.java)
        // Verify
        verifyAll()
        assertNotNull(result)
        assertEquals(expected, result)
    }

    //endregion

    //endregion

}