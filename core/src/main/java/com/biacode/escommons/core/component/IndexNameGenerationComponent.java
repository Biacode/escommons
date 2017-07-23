package com.biacode.escommons.core.component;

import javax.annotation.Nonnull;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public interface IndexNameGenerationComponent {
    @Nonnull
    String generateNameForGivenIndex(@Nonnull final String aliasName);
}
