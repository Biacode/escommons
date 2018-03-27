package org.biacode.escommons.core.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Arthur Asatryan.
 * Date: 3/23/18
 * Time: 12:43 PM
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-escommons-core-test.xml")
public class FooIntegTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooIntegTest.class);

    //region Dependencies
    @Autowired
    private RestHighLevelClient esCommonsRestClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JsonComponent jsonComponent;

    @Autowired
    private ElasticsearchClientWrapper elasticsearchClientWrapper;
    //endregion

    //region Constructors
    public FooIntegTest() {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }
    //endregion

    //region Test methods
    @Test
    public void fooTest() throws IOException {
        elasticsearchClientWrapper.createIndex("escommons", "person", "escommons");
        assert esCommonsRestClient.ping(new BasicHeader("Content-Type", "application/json"));
    }
    //endregion

    public <T> T readFromInputStream(final InputStream is, Class<T> clazz) {
        try {
            return objectMapper.readValue(is, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
