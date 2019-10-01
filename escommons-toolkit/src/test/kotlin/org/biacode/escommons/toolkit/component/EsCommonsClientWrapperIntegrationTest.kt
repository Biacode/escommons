package org.biacode.escommons.toolkit.component

import org.assertj.core.api.Assertions.assertThat
import org.biacode.escommons.core.test.AbstractEsCommonsIntegrationTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/23/17
 * Time: 9:10 PM
 */
class EsCommonsClientWrapperIntegrationTest : AbstractEsCommonsIntegrationTest() {

    override fun mappings(): String = "escommons_test_person"

    //region Dependencies
    @Autowired
    private lateinit var esCommonsClientWrapper: EsCommonsClientWrapper
    //endregion

    //region Test methods
    @Test
    fun `test create index`() {
        // given
        val indexName = UUID.randomUUID().toString()
        // when
        esCommonsClientWrapper.createIndex(indexName, mappings()).let {
            // then
            assertThat(it).isTrue()
        }
    }

    @Test
    fun `test get indices`() {
        // given
        listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .let { expectedIndices ->
                    expectedIndices.forEach { esCommonsClientWrapper.createIndex(it, mappings()) }
                    // when
                    esCommonsClientWrapper.getIndices().let { indices ->
                        // then
                        assertThat(indices).isNotNull.isNotEmpty.containsAll(expectedIndices)
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
    fun `test remove alias`() {
        // given
        createDummyIndex().let { indexName ->
            // when
            val aliasName = UUID.randomUUID().toString()
            esCommonsClientWrapper.addAlias(indexName, aliasName)
            esCommonsClientWrapper.refreshIndex(indexName)
            esCommonsClientWrapper.removeAlias(indexName, aliasName).let {
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
    private fun createDummyIndex(): String {
        val indexName = UUID.randomUUID().toString()
        esCommonsClientWrapper.createIndex(indexName, mappings())
        return indexName
    }
    //endregion
}
