package com.biacode.escommons.core.component.impl;

import com.biacode.escommons.core.component.ClasspathResourceReaderComponent;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
public class ClasspathResourceReaderComponentImpl implements ClasspathResourceReaderComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathResourceReaderComponentImpl.class);

    //region Constructors
    public ClasspathResourceReaderComponentImpl() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @Override
    public Optional<String> readFileFromClasspath(@Nonnull final String path) {
        Assert.notNull(path, "The path should not be null");
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(IOUtils.toString(inputStream, Charset.defaultCharset()));
        } catch (final IOException ex) {
            LOGGER.error("Unexpected error - {} occurs while trying to read file from classpath - {}", ex, path);
            return Optional.empty();
        }
    }
    //endregion
}
