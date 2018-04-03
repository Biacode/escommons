package org.biacode.escommons.core.test.configuration.node

import org.elasticsearch.common.settings.Settings
import org.elasticsearch.node.InternalSettingsPreparer
import org.elasticsearch.node.Node
import org.elasticsearch.plugins.Plugin

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
data class EsCommonsEmbeddedNode(val settings: Settings, var classpathPlugins: Collection<Class<out Plugin>>?)
    : Node(InternalSettingsPreparer.prepareEnvironment(settings, null), classpathPlugins)
