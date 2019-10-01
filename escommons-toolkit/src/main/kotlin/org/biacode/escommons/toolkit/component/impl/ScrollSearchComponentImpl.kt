package org.biacode.escommons.toolkit.component.impl

import org.biacode.escommons.core.model.document.AbstractEsDocument
import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.biacode.escommons.toolkit.component.ScrollSearchComponent
import org.biacode.escommons.toolkit.component.SearchResponseComponent
import org.elasticsearch.action.search.SearchRequestBuilder
import org.elasticsearch.client.Client
import org.elasticsearch.common.unit.TimeValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.Assert
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 9/5/17
 * Time: 4:51 PM
 */
class ScrollSearchComponentImpl : ScrollSearchComponent {

    //region Dependencies
    @Value("\${escommons.scroll.size}")
    private var scrollChunkSize: Int? = null

    @Autowired
    private lateinit var esClient: Client

    @Autowired
    private lateinit var searchResponseComponent: SearchResponseComponent
    //endregion

    //region Public methods
    override fun <T : AbstractEsDocument> scroll(searchRequestBuilder: SearchRequestBuilder,
                                                 clazz: Class<T>,
                                                 timeoutMillis: Long): DocumentsAndTotalCount<T> {
        Assert.isTrue(timeoutMillis > 0, "The timeout millis should be greater than 0")
        val documents = ArrayList<T>()
        var searchResponse = searchRequestBuilder
                .setScroll(TimeValue(timeoutMillis))
                .setSize(scrollChunkSize ?: DEFAULT_SCROLL_CHUNK_SIZE).execute().actionGet()
        while (true) {
            documents.addAll(searchResponseComponent.documents(searchResponse, clazz))
            searchResponse = esClient.prepareSearchScroll(searchResponse.scrollId).setScroll(TimeValue(timeoutMillis)).execute().actionGet()
            if (searchResponse.hits.hits.isEmpty()) {
                break
            }
        }
        return DocumentsAndTotalCount(documents, documents.size.toLong())
    }
    //endregion

    //region Companion object
    companion object {
        private const val DEFAULT_SCROLL_CHUNK_SIZE = 100
    }
    //endregion
}
