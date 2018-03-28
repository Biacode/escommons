package org.biacode.escommons.core.test

import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 9/6/17
 * Time: 12:34 PM
 */
internal object IntegrationTestHelper {

    //region Ducet sort
    private const val DUCET_SORT_TOKENIZER_KEY = "analysis.analyzer.ducet_sort.tokenizer"
    private const val DUCET_SORT_FILTER_KEY = "analysis.analyzer.ducet_sort.filter"
    private const val DUCET_SORT_TOKENIZER_VALUE = "keyword"
    private val DUCET_SORT_FILTER_VALUE = arrayOf("icu_collation")
    //endregion

    //region Lowercase normalizer
    private const val LOWERCASE_NORMALIZER_TYPE = "analysis.normalizer.lowernormalizer.type"
    private const val LOWERCASE_NORMALIZER_TYPE_VALUE = "custom"
    private const val LOWERCASE_NORMALIZER_FILTER = "analysis.normalizer.lowernormalizer.filter"
    private val LOWERCASE_NORMALIZER_FILTER_VALUE = arrayOf("lowercase", "asciifolding")
    //endregion

    //region Public methods
    val settings: Map<String, Any>
        get() {
            val settings = HashMap<String, Any>()
            settings[DUCET_SORT_TOKENIZER_KEY] = DUCET_SORT_TOKENIZER_VALUE
            settings[DUCET_SORT_FILTER_KEY] = DUCET_SORT_FILTER_VALUE
            settings[LOWERCASE_NORMALIZER_TYPE] = LOWERCASE_NORMALIZER_TYPE_VALUE
            settings[LOWERCASE_NORMALIZER_FILTER] = LOWERCASE_NORMALIZER_FILTER_VALUE
            return settings
        }
    //endregion

}