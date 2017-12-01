package org.biacode.escommons.core.component.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.biacode.escommons.core.component.JsonComponent;
import org.biacode.escommons.core.exception.EsCoreRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
public class JsonComponentImpl implements JsonComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonComponentImpl.class);

    //region Constants
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    //endregion

    //region Dependencies
    //endregion

    //region Constructors
    public JsonComponentImpl() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @Nonnull
    @Override
    public <T> T deserialize(@Nonnull final String source, @Nonnull final Class<T> clazz) {
        Assert.notNull(source, "The source should not be null");
        Assert.notNull(clazz, "The clazz should not be null");
        try {
            return OBJECT_MAPPER.reader().forType(clazz).readValue(source);
        } catch (IOException e) {
            LOGGER.error("Error while parsing json - {}", e);
            throw new EsCoreRuntimeException("Error while parsing json", e);
        }
    }

    @Nonnull
    @Override
    public <T> String serialize(@Nonnull final T source, @Nonnull final Class<T> clazz) {
        Assert.notNull(source, "Source should not be null");
        Assert.notNull(clazz, "Clazz should not be null");
        try {
            return OBJECT_MAPPER.writer().forType(clazz).writeValueAsString(source);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while serializing object to json - {}", e);
            throw new EsCoreRuntimeException("Error while serializing object to json", e);
        }
    }
    //endregion
}
