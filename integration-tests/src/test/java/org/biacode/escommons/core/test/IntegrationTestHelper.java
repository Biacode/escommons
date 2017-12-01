package org.biacode.escommons.core.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arthur Asatryan.
 * Date: 9/6/17
 * Time: 12:34 PM
 */
final class IntegrationTestHelper {

    //region Constants
    //region Ducet sort
    private static final String DUCET_SORT_TOKENIZER_KEY = "analysis.analyzer.ducet_sort.tokenizer";
    private static final String DUCET_SORT_FILTER_KEY = "analysis.analyzer.ducet_sort.filter";
    private static final String DUCET_SORT_TOKENIZER_VALUE = "keyword";
    private static final String[] DUCET_SORT_FILTER_VALUE = new String[]{"icu_collation"};
    //endregion

    //region Lowercase normalizer
    private static final String LOWERCASE_NORMALIZER_TYPE = "analysis.normalizer.lowernormalizer.type";
    private static final String LOWERCASE_NORMALIZER_TYPE_VALUE = "custom";
    private static final String LOWERCASE_NORMALIZER_FILTER = "analysis.normalizer.lowernormalizer.filter";
    private static final String[] LOWERCASE_NORMALIZER_FILTER_VALUE = new String[]{"lowercase", "asciifolding"};
    //endregion
    //endregion

    //region Constructors
    private IntegrationTestHelper() {
    }
    //endregion

    //region Public static methods
    public static Map<String, Object> getSettings() {
        final Map<String, Object> settings = new HashMap<>();
        settings.put(DUCET_SORT_TOKENIZER_KEY, DUCET_SORT_TOKENIZER_VALUE);
        settings.put(DUCET_SORT_FILTER_KEY, DUCET_SORT_FILTER_VALUE);
        settings.put(LOWERCASE_NORMALIZER_TYPE, LOWERCASE_NORMALIZER_TYPE_VALUE);
        settings.put(LOWERCASE_NORMALIZER_FILTER, LOWERCASE_NORMALIZER_FILTER_VALUE);
        return settings;
    }
    //endregion

}
