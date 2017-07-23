package com.biacode.escommons.core.component;

import org.joda.time.DateTime;

import javax.annotation.Nonnull;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public interface ClockComponent {
    @Nonnull
    String printCurrentDateTimeWithPredefinedFormatter();

    @Nonnull
    DateTime getCurrentDateTime();
}
