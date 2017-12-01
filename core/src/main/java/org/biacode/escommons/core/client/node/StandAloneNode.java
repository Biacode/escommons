package org.biacode.escommons.core.client.node;

import org.elasticsearch.env.Environment;
import org.elasticsearch.node.Node;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public class StandAloneNode extends Node {

    //region Constructor
    public StandAloneNode(final Environment environment, final Collection<Class<? extends Plugin>> classpathPlugins) {
        super(environment, classpathPlugins);
    }
    //endregion

}
