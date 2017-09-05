package com.biacode.escommons.core.component.impl;

import com.biacode.escommons.core.component.ScrollSearchComponent;
import com.biacode.escommons.core.component.SearchResponseComponent;
import com.biacode.escommons.core.model.document.AbstractEsDocument;
import com.biacode.escommons.core.model.response.DocumentsAndTotalCount;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    //region Dependencies
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
                                                                                      final long keepAlive) {
        Assert.notNull(searchRequestBuilder, "The search request builder should not be null");
        Assert.notNull(clazz, "The document clazz should not be null");
        Assert.isTrue(keepAlive > 0, "The keep alive should be greater than 0");
        final List<T> documents = new ArrayList<>();
        SearchResponse searchResponse = searchRequestBuilder
                .setScroll(new TimeValue(60000))
                .setSize(100).execute().actionGet();
        while (true) {
            documents.addAll(searchResponseComponent.convertSearchResponseToDocuments(searchResponse, clazz));
            searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            if (searchResponse.getHits().getHits().length == 0) {
                break;
            }
        }
        return new DocumentsAndTotalCount<>(documents, documents.size());
    }
    //endregion
}
