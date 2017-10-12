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
    /**
     * Gets scroll response.
     * <p>NOTE: The default chunk size is 100. See foo.boo property for custom configuration.</p>
     *
     * @param <T>                  the type parameter
     * @param searchRequestBuilder the search request builder
     * @param clazz                the clazz
     * @param timeoutMillis        the timeout millis
     * @return scroll response
     */
    @Nonnull
    <T extends AbstractEsDocument> DocumentsAndTotalCount<T> getScrollResponse(
            @Nonnull final SearchRequestBuilder searchRequestBuilder,
            @Nonnull final Class<T> clazz,
            final long timeoutMillis
    );
}