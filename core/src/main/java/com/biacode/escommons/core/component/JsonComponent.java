package com.biacode.escommons.core.component;

import javax.annotation.Nonnull;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public interface JsonComponent {
    /**
     * Deserialize source to document.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param clazz  the clazz
     * @return the deserialize document
     */
    @Nonnull
    <T> T deserialize(@Nonnull final String source, @Nonnull final Class<T> clazz);

    /**
     * Serialize document to string
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param clazz  the clazz
     * @return the serialize string
     */
    @Nonnull
    <T> String serialize(@Nonnull final T source, @Nonnull final Class<T> clazz);
}
