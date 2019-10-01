package org.biacode.escommons.core.test

import org.biacode.escommons.core.test.configuration.EsCommonsTestAnnotationDrivenConfiguration
import org.biacode.escommons.toolkit.component.EsCommonsClientWrapper
import org.elasticsearch.common.settings.Settings
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 1:16 PM
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = [EsCommonsTestAnnotationDrivenConfiguration::class])
abstract class AbstractEsCommonsIntegrationTest {

    //region Properties
    protected var indexName: String = UUID.randomUUID().toString()

    protected var settings: Settings = Settings.EMPTY
    //endregion

    //region Dependencies
    @Autowired
    private lateinit var esCommonsClientWrapper: EsCommonsClientWrapper
    //endregion

    //region Test callbacks
    @Before
    fun beforeEsCommonsTests() {
        cleanUpIndices()
        assert(esCommonsClientWrapper.createIndex(indexName, mappings(), settings))
        refreshIndex(indexName)
    }
    //endregion

    //region Abstract methods
    abstract fun mappings(): String
    //endregion

    //region Protected methods
    protected fun refreshIndex(indexName: String) {
        esCommonsClientWrapper.refreshIndex(indexName)
    }
    //endregion

    //region Utility methods
    protected fun cleanUpIndices() {
        esCommonsClientWrapper.deleteIndices("*")
    }
    //endregion
}
