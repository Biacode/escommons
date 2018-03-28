package org.biacode.escommons.core;

import org.apache.commons.io.FileUtils;
import org.biacode.escommons.core.exception.EsCommonsCoreRuntimeException;
import org.elasticsearch.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 3/23/18
 * Time: 12:26 PM
 */
public class PluginUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginUtils.class);

    //region Utility methods
    public void addPlugins(final Environment environment) {
        LOGGER.debug("Trying to add additional plugins from classpath.");
        getPluginsPath().ifPresent(it -> {
            try {
                final String pluginFile = it.getFile();
                final File pluginsFile = environment.pluginsFile().toFile();
                LOGGER.debug("Copying plugin - {} to plugins file - {}", pluginFile);
                FileUtils.copyDirectory(new File(it.getFile()), pluginsFile);
            } catch (final IOException e) {
                LOGGER.error("Error occurred while trying to load addition plugins {}", e);
                throw new EsCommonsCoreRuntimeException("Error occurred while trying to load addition plugins", e);
            }
        });
    }

    public Optional<URL> getPluginsPath() {
        return Optional.ofNullable(getClass().getClassLoader().getResource("plugins"));
    }
    //endregion

}
