package org.biacode.escommons.core.component.impl;

import org.biacode.escommons.core.component.ScrollSearchComponent;
import org.biacode.escommons.core.component.SearchResponseComponent;
import org.biacode.escommons.core.model.document.AbstractEsDocument;
import org.biacode.escommons.core.model.response.DocumentsAndTotalCount;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 9/5/17
 * Time: 4:51 PM
 */
@Component
public class ScrollSearchComponentImpl implements ScrollSearchComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrollSearchComponentImpl.class);

    //region Constants
    private static final int DEFAULT_SCROLL_CHUNK_SIZE = 100;
    //endregion

    //region Dependencies
    @Value("${escommons.scroll.chunk.size}")
    private Integer scrollChunkSize;

    @Autowired
    private Client esClient;

    @Autowired
    private SearchResponseComponent searchResponseComponent;
    //endregion

    //region Constructors
    public ScrollSearchComponentImpl() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @Nonnull
    @Override
    public <T extends AbstractEsDocument> DocumentsAndTotalCount<T> getScrollResponse(@Nonnull final SearchRequestBuilder searchRequestBuilder,
                                                                                      @Nonnull final Class<T> clazz,
                                                                                      final long timeoutMillis) {
        Assert.notNull(searchRequestBuilder, "The search request builder should not be null");
        Assert.notNull(clazz, "The document clazz should not be null");
        Assert.isTrue(timeoutMillis > 0, "The timeout millis should be greater than 0");
        final List<T> documents = new ArrayList<>();
        SearchResponse searchResponse = searchRequestBuilder
                .setScroll(new TimeValue(timeoutMillis))
                .setSize(scrollChunkSize == null ? DEFAULT_SCROLL_CHUNK_SIZE : scrollChunkSize).execute().actionGet();
        while (true) {
            documents.addAll(searchResponseComponent.convertSearchResponseToDocuments(searchResponse, clazz));
            searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(timeoutMillis)).execute().actionGet();
            if (searchResponse.getHits().getHits().length == 0) {
                break;
            }
        }
        return new DocumentsAndTotalCount<>(documents, documents.size());
    }
    //endregion
}
