package org.biacode.escommons.toolkit.component

import org.biacode.escommons.core.model.document.AbstractEsDocument
import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.elasticsearch.action.search.SearchRequestBuilder

/**
 * Created by Arthur Asatryan.
 * Date: 9/5/17
 * Time: 4:49 PM
 */
interface ScrollSearchComponent {
    fun <T : AbstractEsDocument> scroll(
            searchRequestBuilder: SearchRequestBuilder,
            clazz: Class<T>,
            timeoutMillis: Long
    ): DocumentsAndTotalCount<T>
}