package org.biacode.escommons.toolkit.component

import org.biacode.escommons.core.model.document.AbstractEsDocument
import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.search.SearchResponse

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
interface SearchResponseComponent {
    /**
     * Converts search response to documents and total count.
     *
     * @param <T>            the type parameter
     * @param searchResponse the search response
     * @param clazz          the clazz
     * @return the documents and total count
    </T> */
    fun <T : AbstractEsDocument> convertToDocumentsAndTotalCount(searchResponse: SearchResponse, clazz: Class<T>): DocumentsAndTotalCount<T>

    /**
     * Converts get response to documents and total count
     *
     * @param <T>         the type parameter
     * @param getResponse the get response
     * @param clazz       the clazz
     * @return the documents and total count
    </T> */
    fun <T : AbstractEsDocument> convertGetResponseToDocument(getResponse: GetResponse, clazz: Class<T>): T

    /**
     * Convert search response to documents list.
     *
     * @param <T>         the type parameter
     * @param getResponse the get response
     * @param clazz       the clazz
     * @return the documents list
    </T> */
    fun <T : AbstractEsDocument> convertSearchResponseToDocuments(getResponse: SearchResponse, clazz: Class<T>): List<T>

    /**
     * Extracts _id fields from given search response
     *
     * @param searchResponse the search response
     * @return list of ids
     */
    fun convertToIdsList(searchResponse: SearchResponse): List<String>
}
