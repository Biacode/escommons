package org.biacode.escommons.core.component;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public interface ClasspathResourceReaderComponent {
    Optional<String> readFileFromClasspath(@Nonnull final String path);
}
