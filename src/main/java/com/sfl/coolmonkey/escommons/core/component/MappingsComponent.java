package com.sfl.coolmonkey.escommons.core.component;

import javax.annotation.Nonnull;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public interface MappingsComponent {
    /**
     * Read mappings for index name and type.
     *
     * @param aliasName the index name
     * @param type      the type
     * @return the mappings
     */
    @Nonnull
    String readMappings(@Nonnull final String aliasName, @Nonnull final String type);
}
