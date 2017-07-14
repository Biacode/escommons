package com.sfl.coolmonkey.escommons.core.component;

import com.sfl.coolmonkey.escommons.core.model.document.AbstractEsDocument;
import com.sfl.coolmonkey.escommons.core.model.response.DocumentsAndTotalCount;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public interface SearchResponseComponent {
    /**
     * Converts search response to documents and total count.
     *
     * @param <T>            the type parameter
     * @param searchResponse the search response
     * @param clazz          the clazz
     * @return the documents and total count
     */
    @Nonnull
    <T extends AbstractEsDocument> DocumentsAndTotalCount<T> convertToDocumentsAndTotalCount(@Nonnull final SearchResponse searchResponse, @Nonnull final Class<T> clazz);

    /**
     * Converts get response to documents and total count
     *
     * @param <T>         the type parameter
     * @param getResponse the get response
     * @param clazz       the clazz
     * @return the documents and total count
     */
    @Nonnull
    <T extends AbstractEsDocument> T convertGetResponseToDocument(@Nonnull final GetResponse getResponse, @Nonnull final Class<T> clazz);

    /**
     * Convert search response to documents list.
     *
     * @param <T>         the type parameter
     * @param getResponse the get response
     * @param clazz       the clazz
     * @return the documents list
     */
    @Nonnull
    <T extends AbstractEsDocument> List<T> convertSearchResponseToDocuments(@Nonnull final SearchResponse getResponse, @Nonnull final Class<T> clazz);

    /**
     * Extracts _id fields from given search response
     *
     * @param searchResponse the search response
     * @return list of ids
     */
    @Nonnull
    List<String> convertToIdsList(final SearchResponse searchResponse);
}
