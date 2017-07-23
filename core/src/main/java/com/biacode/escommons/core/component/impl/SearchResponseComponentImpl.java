package com.biacode.escommons.core.component.impl;

import com.biacode.escommons.core.component.JsonComponent;
import com.biacode.escommons.core.component.SearchResponseComponent;
import com.biacode.escommons.core.model.document.AbstractEsDocument;
import com.biacode.escommons.core.model.response.DocumentsAndTotalCount;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
public class SearchResponseComponentImpl implements SearchResponseComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchResponseComponentImpl.class);

    //region Dependencies
    @Autowired
    private JsonComponent jsonComponent;
    //endregion

    //region Constructors
    public SearchResponseComponentImpl() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @Nonnull
    @Override
    public <T extends AbstractEsDocument> DocumentsAndTotalCount<T> convertToDocumentsAndTotalCount(@Nonnull final SearchResponse searchResponse,
                                                                                                    @Nonnull final Class<T> clazz) {
        assertSearchResponseNotNull(searchResponse);
        assertClazzNotNull(clazz);
        final SearchHits searchHits = searchResponse.getHits();
        final List<T> documents = extractDocuments(searchHits, clazz);
        return new DocumentsAndTotalCount<>(documents, searchHits.getTotalHits());
    }

    @Nonnull
    @Override
    public <T extends AbstractEsDocument> T convertGetResponseToDocument(@Nonnull final GetResponse getResponse,
                                                                         @Nonnull final Class<T> clazz) {
        Assert.notNull(getResponse, "The get response should not be null");
        assertClazzNotNull(clazz);
        final T document = jsonComponent.deserialize(getResponse.getSourceAsString(), clazz);
        document.setUuid(getResponse.getId());
        return document;
    }

    @Nonnull
    @Override
    public <T extends AbstractEsDocument> List<T> convertSearchResponseToDocuments(@Nonnull final SearchResponse searchResponse,
                                                                                   @Nonnull final Class<T> clazz) {
        assertSearchResponseNotNull(searchResponse);
        assertClazzNotNull(clazz);
        return extractDocuments(searchResponse.getHits(), clazz);
    }

    @Nonnull
    @Override
    public List<String> convertToIdsList(@Nonnull final SearchResponse searchResponse) {
        assertSearchResponseNotNull(searchResponse);
        return Arrays
                .stream(searchResponse.getHits().getHits())
                .map(SearchHit::getId)
                .collect(Collectors.toList());
    }
    //endregion

    //region Utility methods
    private <T extends AbstractEsDocument> List<T> extractDocuments(final SearchHits searchHits, final Class<T> clazz) {
        return Stream
                .of(searchHits.getHits())
                .map(searchHitFields -> {
                    final T document = jsonComponent.deserialize(searchHitFields.getSourceAsString(), clazz);
                    document.setUuid(searchHitFields.getId());
                    return document;
                })
                .collect(Collectors.toList());
    }

    private <T extends AbstractEsDocument> void assertClazzNotNull(final Class<T> clazz) {
        Assert.notNull(clazz, "The clazz should not be null");
    }

    private void assertSearchResponseNotNull(final SearchResponse searchResponse) {
        Assert.notNull(searchResponse, "The search response should not be null");
    }
    //endregion
}
