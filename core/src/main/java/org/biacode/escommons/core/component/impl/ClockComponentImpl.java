package org.biacode.escommons.core.component.impl;

import org.biacode.escommons.core.component.ClockComponent;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
public class ClockComponentImpl implements ClockComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClockComponentImpl.class);

    //region Constants
    private static final String DATE_TIME_PATTERN = "yyyy_MM_dd_HH_mm_ss_SSS";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(DATE_TIME_PATTERN);
    //endregion

    //region Dependencies
    //endregion

    //region Constructors
    public ClockComponentImpl() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @Nonnull
    @Override
    public String printCurrentDateTimeWithPredefinedFormatter() {
        return DATE_TIME_FORMATTER.print(DateTime.now());
    }

    @Nonnull
    @Override
    public DateTime getCurrentDateTime() {
        return DateTime.now();
    }
    //endregion
}
