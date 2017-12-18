package org.biacode.escommons.core.component.impl;

import org.biacode.escommons.core.component.ClockComponent;
import org.biacode.escommons.core.component.IndexNameGenerationComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
public class IndexNameGenerationComponentImpl implements IndexNameGenerationComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexNameGenerationComponentImpl.class);

    //region Dependencies
    @Autowired
    private ClockComponent clockComponent;
    //endregion

    //region Constructors
    public IndexNameGenerationComponentImpl() {
        LOGGER.debug("Initializing index name generation component");
    }
    //endregion

    //region Public methods
    @Nonnull
    @Override
    public String generateNameForGivenIndex(@Nonnull final String aliasName) {
        Assert.notNull(aliasName, "The index name should not be null");
        return aliasName + "_date_" + clockComponent.printCurrentDateTimeWithPredefinedFormatter() + "_uuid_" + UUID.randomUUID().toString();
    }
    //endregion
}
