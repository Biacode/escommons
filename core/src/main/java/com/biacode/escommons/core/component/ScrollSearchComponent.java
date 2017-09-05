package com.biacode.escommons.core.component;

import com.biacode.escommons.core.model.document.AbstractEsDocument;
import com.biacode.escommons.core.model.response.DocumentsAndTotalCount;
import org.elasticsearch.action.search.SearchRequestBuilder;

import javax.annotation.Nonnull;

/**
 * Created by Arthur Asatryan.
 * Date: 9/5/17
 * Time: 4:49 PM
 */
public interface ScrollSearchComponent {
    @Nonnull
    <T extends AbstractEsDocument> DocumentsAndTotalCount<T> getScrollResponse(@Nonnull final SearchRequestBuilder searchRequestBuilder, @Nonnull final Class<T> clazz, final long keepAlive);
}