package com.biacode.escommons.core.repository.impl;

import com.biacode.escommons.core.component.JsonComponent;
import com.biacode.escommons.core.component.SearchResponseComponent;
import com.biacode.escommons.core.model.document.AbstractEsDocument;
import com.biacode.escommons.core.model.response.DocumentsAndTotalCount;
import com.biacode.escommons.core.repository.EsRepository;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.*;

import static com.biacode.escommons.core.model.document.DocumentConstants.DATE_FORMAT;
import static com.biacode.escommons.core.model.document.DocumentConstants.FETCH_MAX_SIZE;
import static java.util.stream.Collectors.toMap;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 3:37 PM
 *
 * @param <T> the document which is subtype of AbstractEsDocument
 */
@Component
public abstract class AbstractEsRepository<T extends AbstractEsDocument> implements EsRepository<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEsRepository.class);

    //region Dependencies
    @Autowired
    private Client esClient;

    @Autowired
    private SearchResponseComponent searchResponseComponent;

    @Autowired
    private JsonComponent jsonComponent;
    //endregion

    //region Private properties
    private final Class<T> clazz;
    //endregion

    //region Constructors
    @SuppressWarnings("unchecked")
    public AbstractEsRepository() {
        LOGGER.debug("Initializing");
        this.clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractEsRepository.class);
    }
    //endregion

    //region Concrete methods
    @Nonnull
    @Override
    public T save(@Nonnull final T document, @Nonnull final String indexName) {
        Assert.notNull(document, "The document should not be null");
        assertIndexNameNotNull(indexName);
        esClient.prepareIndex(indexName, getDocumentType())
                .setId(document.getUuid())
                .setSource(jsonComponent.serialize(document, clazz)).get();
        return document;
    }

    @Nonnull
    @Override
    public List<T> save(@Nonnull final List<T> documents, @Nonnull final String indexName) {
        Assert.notNull(documents, "The list of documents should not be null");
        assertIndexNameNotNull(indexName);
        final BulkRequestBuilder bulkBuilder = esClient.prepareBulk();
        documents.forEach(document -> {
            final IndexRequestBuilder indexRequestBuilder = esClient
                    .prepareIndex(indexName, getDocumentType())
                    .setId(document.getUuid())
                    .setSource(jsonComponent.serialize(document, clazz));
            bulkBuilder.add(indexRequestBuilder);
        });
        bulkBuilder.get();
        return documents;
    }

    @Nonnull
    @Override
    public String delete(@Nonnull final String id, @Nonnull final String indexName) {
        return esClient.prepareDelete(indexName, getDocumentType(), id).get().getId();
    }

    @Nonnull
    @Override
    public List<String> delete(@Nonnull final List<String> ids, @Nonnull final String indexName) {
        final BulkRequestBuilder bulkBuilder = esClient.prepareBulk();
        ids.forEach(uuid -> bulkBuilder.add(esClient.prepareDelete(indexName, getDocumentType(), uuid)));
        bulkBuilder.get();
        return ids;
    }

    @Nonnull
    @Override
    public Optional<T> findById(@Nonnull final String id, @Nonnull final String indexName) {
        assertIndexNameNotNull(indexName);
        final GetResponse response = esClient.prepareGet(indexName, getDocumentType(), id).get();
        if (!response.isExists()) {
            return Optional.empty();
        }
        return Optional.of(searchResponseComponent.convertGetResponseToDocument(response, clazz));
    }

    @Nonnull
    @Override
    public DocumentsAndTotalCount<T> findByIds(@Nonnull final List<String> ids, @Nonnull final String indexName) {
        assertIndexNameNotNull(indexName);
        final SearchResponse searchResponse = esClient.prepareSearch(indexName)
                .setQuery(boolQuery().filter(termsQuery("uuid", ids)))
                .setSize(FETCH_MAX_SIZE)
                .setTypes(getDocumentType())
                .get();
        return searchResponseComponent.convertToDocumentsAndTotalCount(searchResponse, clazz);
    }

    @Nonnull
    @Override
    public Map<Object, Object> findByField(@Nonnull final String searchField,
                                           @Nonnull final List<Object> terms,
                                           @Nonnull final String resultField,
                                           @Nonnull final String indexName,
                                           @Nonnull final String documentType) {
        final SearchResponse searchResponse = esClient.prepareSearch(indexName)
                .setTypes(documentType)
                .setQuery(boolQuery().must(matchAllQuery()).filter(termsQuery(searchField, terms)))
                .get();
        return Arrays.stream(searchResponse.getHits().getHits())
                .collect(toMap(hit -> hit.getSource().get(searchField), hit -> hit.getSource().get(resultField)));
    }
    //endregion

    //region Abstract methods
    protected abstract String getDocumentType();
    //endregion

    //region Protected methods
    protected Class<T> getClazz() {
        return clazz;
    }

    @Nonnull
    protected String[] stringValues(@Nonnull final Object... values) {
        Assert.notNull(values, "The string values should not be null");
        return Arrays.stream(values).map(this::stringValue).toArray(String[]::new);
    }

    @Nonnull
    protected String stringValue(@Nonnull final Object value) {
        Assert.notNull(value, "The string value should not be null");
        return value.toString();
    }

    @Nonnull
    protected String dateValue(@Nonnull final Object date) {
        Assert.notNull(date, "The date value should not be null");
        return date instanceof Date ? DATE_FORMAT.format(date) : date.toString();
    }

    @Nonnull
    protected String decimalValue(@Nonnull final Object decimal) {
        Assert.notNull(decimal, "The decimal value should not be null");
        return decimal.toString();
    }
    //endregion

    //region Utility methods
    private void assertIndexNameNotNull(final String indexName) {
        Assert.notNull(indexName, "The index name should not be null");
    }
    //endregion
}
