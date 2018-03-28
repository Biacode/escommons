package org.biacode.escommons.core.component

import org.assertj.core.api.Assertions.assertThat
import org.biacode.escommons.core.test.AbstractIntegrationTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/23/17
 * Time: 9:10 PM
 */
class EsCommonsRestClientWrapperIntegrationTest : AbstractIntegrationTest() {

    //region Dependencies
    @Autowired
    private lateinit var esCommonsClientWrapper: EsCommonsClientWrapper
    //endregion

    //region Test methods
    @Test
    fun `test create index`() {
        // given
        val indexName = UUID.randomUUID().toString()
        val type = UUID.randomUUID().toString()
        val mappingsName = "person"
        // when
        esCommonsClientWrapper.createIndex(indexName, type, mappingsName).let {
            // then
            assertThat(it).isTrue()
        }
    }

    @Test
    fun `test get indices`() {
        // given
        listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .let { expectedIndices ->
                    expectedIndices.forEach { esCommonsClientWrapper.createIndex(it, UUID.randomUUID().toString(), "person") }
                    // when
                    esCommonsClientWrapper.getIndices().let { indices ->
                        // then
                        assertThat(indices).isNotNull.isNotEmpty.containsExactlyInAnyOrder(*expectedIndices.toTypedArray())
                    }
                }
    }

    @Test
    fun `test refresh index`() {
        // given
        createDummyIndex().let { indexName ->
            // when
            esCommonsClientWrapper.refreshIndex(indexName)
        }
    }

    @Test
    fun `test add alias`() {
        // given
        createDummyIndex().let { indexName ->
            // when
            val aliasName = UUID.randomUUID().toString()
            esCommonsClientWrapper.addAlias(indexName, aliasName).let {
                // then
                assertThat(it).isTrue()
            }
        }
    }

    @Test
    fun `test delete index`() {
        // given
        createDummyIndex().let { indexName ->
            // when
            esCommonsClientWrapper.deleteIndices(indexName).let {
                // then
                assertThat(it).isTrue()
                // then
                assertThat(esCommonsClientWrapper.getIndices()).doesNotContain(indexName)
            }
        }
    }

    @Test
    fun `test index exists`() {
        // given
        createDummyIndex().let { indexName ->
            // when
            esCommonsClientWrapper.indexExists(indexName).let {
                // then
                assertThat(it).isTrue()
            }
        }
    }
    //endregion

    //region Utility methods
    fun createDummyIndex(): String {
        val indexName = UUID.randomUUID().toString()
        val type = UUID.randomUUID().toString()
        val mappingsName = "person"
        esCommonsClientWrapper.createIndex(indexName, type, mappingsName)
        return indexName
    }
    //endregion
}
