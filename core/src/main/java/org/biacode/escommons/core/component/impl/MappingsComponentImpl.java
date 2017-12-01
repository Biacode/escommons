package org.biacode.escommons.core.component.impl;

import org.biacode.escommons.core.component.ClasspathResourceReaderComponent;
import org.biacode.escommons.core.component.MappingsComponent;
import org.biacode.escommons.core.exception.EsCoreRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
public class MappingsComponentImpl implements MappingsComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingsComponentImpl.class);

    //region Dependencies
    @Autowired
    private ClasspathResourceReaderComponent classpathResourceReaderComponent;
    //endregion

    //region Constructors
    public MappingsComponentImpl() {
        LOGGER.debug("Initializing mappings component");
    }
    //endregion

    //region Public methods
    @Nonnull
    @Override
    public String readMappings(@Nonnull final String aliasName, @Nonnull final String type) {
        Assert.notNull(aliasName, "The index name should not be null");
        Assert.notNull(type, "The type should not be null");
        return classpathResourceReaderComponent.readFileFromClasspath(getMappingPath(aliasName, type))
                .orElseThrow(() -> {
                    LOGGER.error("Can not read mappings for alias name - {} and document type - {}", aliasName, type);
                    return new EsCoreRuntimeException("Can not read mappings for alias name and document type");
                });
    }
    //endregion

    //region Utility methods
    private String getMappingPath(final String aliasName, final String type) {
        return "mappings/" + aliasName + "/" + type + ".json";
    }
    //endregion
}
