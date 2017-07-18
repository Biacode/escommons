package com.sfl.coolmonkey.escommons.core.component.impl;

import com.sfl.coolmonkey.escommons.core.component.JsonComponent;
import com.sfl.coolmonkey.escommons.core.component.SearchResponseComponent;
import com.sfl.coolmonkey.escommons.core.model.document.AbstractEsDocument;
import com.sfl.coolmonkey.escommons.core.model.response.DocumentsAndTotalCount;
import com.sfl.coolmonkey.escommons.core.test.AbstractCoreUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 5:07 PM
 */
public class SearchResponseComponentImplTest extends AbstractCoreUnitTest {

    //region Test subject and mocks
    @TestSubject
    private SearchResponseComponent searchResponseComponent = new SearchResponseComponentImpl();

    @Mock
    private JsonComponent jsonComponent;
    //endregion

    //region Constructors
    public SearchResponseComponentImplTest() {
    }
    //endregion

    //region Test methods

    //region convertToDocumentsAndTotalCount
    @Test
    public void testConvertToDocumentsAndTotalCountWithInvalidArguments() {
        // Test data
        final SearchResponse validSearchResponse = new SearchResponse();
        final Class<AbstractEsDocument> validClass = AbstractEsDocument.class;
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            searchResponseComponent.convertToDocumentsAndTotalCount(null, validClass);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            searchResponseComponent.convertToDocumentsAndTotalCount(validSearchResponse, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertToDocumentsAndTotalCount() {
        // Test data
        final SearchResponse searchResponse = createMock(SearchResponse.class);
        final Class<Person> clazz = Person.class;
        final SearchHits searchHits = createMock(SearchHits.class);
        final SearchHit searchHit1 = createMock(SearchHit.class);
        final SearchHit searchHit2 = createMock(SearchHit.class);
        final SearchHit[] searchHitsArray = new SearchHit[]{searchHit1, searchHit2};
        final String searchHit1Source = "search hit 1 source";
        final String searchHit2Source = "search hit 1 source";
        final String searchHit1Id = "search hit 1 id";
        final String searchHit2Id = "search hit 1 id";
        final Person document1 = new Person();
        final Person document2 = new Person();
        final long totalHits = 3L;
        // Reset
        resetAll();
        // Expectations
        expect(searchResponse.getHits()).andReturn(searchHits);
        expect(searchHits.getHits()).andReturn(searchHitsArray);
        expect(searchHit1.getSourceAsString()).andReturn(searchHit1Source);
        expect(searchHit1.getId()).andReturn(searchHit1Id);
        expect(jsonComponent.deserialize(searchHit1Source, clazz)).andReturn(document1);
        expect(searchHit2.getSourceAsString()).andReturn(searchHit2Source);
        expect(searchHit2.getId()).andReturn(searchHit2Id);
        expect(jsonComponent.deserialize(searchHit2Source, clazz)).andReturn(document2);
        expect(searchHits.getTotalHits()).andReturn(totalHits);
        // Replay
        replayAll();
        // Run test scenario
        final DocumentsAndTotalCount<Person> response = searchResponseComponent.convertToDocumentsAndTotalCount(searchResponse, clazz);
        // Verify
        verifyAll();
        assertNotNull(response);
        assertEquals(totalHits, response.getTotalCount());
        assertNotNull(response.getDocuments());
        assertEquals(Arrays.asList(document1, document2), response.getDocuments());
        assertEquals(searchHit1Id, response.getDocuments().get(0).getUuid());
        assertEquals(searchHit2Id, response.getDocuments().get(1).getUuid());
    }
    //endregion

    //region convertGetResponseToDocument
    @Test
    public void testConvertGetResponseToDocumentWithInvalidArguments() {
        // Test data
        final GetResponse validGetResponse = createMock(GetResponse.class);
        final Class<AbstractEsDocument> validClass = AbstractEsDocument.class;
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            searchResponseComponent.convertGetResponseToDocument(null, validClass);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            searchResponseComponent.convertGetResponseToDocument(validGetResponse, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertGetResponseToDocument() {
        // Test data
        final GetResponse getResponse = createMock(GetResponse.class);
        final Class<AbstractEsDocument> clazz = AbstractEsDocument.class;
        final String source = "source";
        final String id = "id";
        final Person document = new Person();
        // Reset
        resetAll();
        // Expectations
        expect(getResponse.getSourceAsString()).andReturn(source);
        expect(jsonComponent.deserialize(source, clazz)).andReturn(document);
        expect(getResponse.getId()).andReturn(id);
        // Replay
        replayAll();
        // Run test scenario
        final AbstractEsDocument response = searchResponseComponent.convertGetResponseToDocument(getResponse, clazz);
        // Verify
        verifyAll();
        assertNotNull(response);
        assertEquals(document, response);
        assertEquals(id, response.getUuid());
    }
    //endregion

    //region convertToIdsList
    @Test
    public void testConvertToIdsListWithInvalidArguments() {
        // Test data
        // Reset
        resetAll();
        // Expectations
        // Replay
        replayAll();
        // Run test scenario
        try {
            searchResponseComponent.convertToIdsList(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertToIdsList() {
        // Test data
        final SearchResponse searchResponse = createMock(SearchResponse.class);
        final SearchHits searchHits = createMock(SearchHits.class);
        final SearchHit searchHit1 = createMock(SearchHit.class);
        final SearchHit searchHit2 = createMock(SearchHit.class);
        final SearchHit[] searchHitsArray = new SearchHit[]{searchHit1, searchHit2};
        final String searchHit1Id = "search hit 1 id";
        final String searchHit2Id = "search hit 1 id";
        // Reset
        resetAll();
        // Expectations
        expect(searchResponse.getHits()).andReturn(searchHits);
        expect(searchHits.getHits()).andReturn(searchHitsArray);
        expect(searchHit1.getId()).andReturn(searchHit1Id);
        expect(searchHit2.getId()).andReturn(searchHit2Id);
        // Replay
        replayAll();
        // Run test scenario
        final List<String> response = searchResponseComponent.convertToIdsList(searchResponse);
        // Verify
        verifyAll();
        assertEquals(Arrays.asList(searchHit1Id, searchHit2Id), response);
    }
    //endregion

    //endregion

}