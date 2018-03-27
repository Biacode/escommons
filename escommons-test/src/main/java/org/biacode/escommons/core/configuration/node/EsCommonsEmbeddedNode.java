package org.biacode.escommons.core.configuration.node;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
import org.elasticsearch.plugins.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public class EsCommonsEmbeddedNode extends Node {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsCommonsEmbeddedNode.class);

    //region Constructor
    public EsCommonsEmbeddedNode(final Settings settings, final Collection<Class<? extends Plugin>> classpathPlugins) {
        super(InternalSettingsPreparer.prepareEnvironment(settings, null), classpathPlugins);
        LOGGER.debug("Creating escommons embedded node with settings - {} and plugins - {}", settings, classpathPlugins);
    }
    //endregion

}
