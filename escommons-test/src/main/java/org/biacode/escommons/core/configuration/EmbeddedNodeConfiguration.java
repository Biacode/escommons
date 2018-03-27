package org.biacode.escommons.core.configuration;

import org.apache.commons.io.IOUtils;
import org.biacode.escommons.core.configuration.node.EsCommonsEmbeddedNode;
import org.biacode.escommons.core.exception.EsCoreRuntimeException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.plugins.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 * Created by Arthur Asatryan.
 * Date: 3/23/18
 * Time: 12:08 PM
 */
@Configuration
public class EmbeddedNodeConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedNodeConfiguration.class);

    //region Dependencies
    @Value("${escommons.host}")
    private String host;

    @Value("${escommons.port}")
    private Integer port;

    @Value("${escommons.cluster.name}")
    private String esCommonsClusterName;

    @Value("${escommons.path.home}")
    private String esCommonsPathHome;

    @Value("${escommons.path.data}")
    private String esCommonsPathData;

    @Value("${escommons.path.repo}")
    private String esCommonsPathRepo;

    @Value("${escommons.http.type}")
    private String esCommonsHttpType;

    @Value("${escommons.http.port}")
    private Integer esCommonsHttpPort;

    @Value("${escommons.http.enabled}")
    private Boolean esCommonsHttpEnabled;

    @Value("${escommons.node.max_local_storage_nodes}")
    private Integer esCommonsNodeMaxLocalStorageNodes;

    @Value("${escommons.plugins}")
    private Collection<Class<? extends Plugin>> plugins;
    //endregion

    //region Constructors
    public EmbeddedNodeConfiguration() {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }
    //endregion

    //region Public methods
    @Bean
    public Client embeddedServer() {
        try {
            final Settings settings = Settings.builder()
                    .put("cluster.name", esCommonsClusterName)
                    .put("path.home", esCommonsPathHome)
                    .put("path.data", esCommonsPathData)
                    .put("path.repo", esCommonsPathRepo)
                    .put("http.type", esCommonsHttpType)
                    .put("http.port", esCommonsHttpPort)
                    .put("http.enabled", esCommonsHttpEnabled)
                    .put("node.max_local_storage_nodes", esCommonsNodeMaxLocalStorageNodes)
                    .build();
            final Node node = new EsCommonsEmbeddedNode(settings, plugins);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> IOUtils.closeQuietly(node)));
            return node.start().client();
        } catch (final NodeValidationException e) {
            LOGGER.error("Error occurs while initializing elastic search node at {}:{} - {}", host, port, e);
            throw new EsCoreRuntimeException("Error occurs while initializing elastic search node at " + host + ":" + port, e);
        }
    }
    //endregion

    //region Properties getters and setters
    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(final Integer port) {
        this.port = port;
    }

    public String getEsCommonsClusterName() {
        return esCommonsClusterName;
    }

    public void setEsCommonsClusterName(final String esCommonsClusterName) {
        this.esCommonsClusterName = esCommonsClusterName;
    }

    public String getEsCommonsPathHome() {
        return esCommonsPathHome;
    }

    public void setEsCommonsPathHome(final String esCommonsPathHome) {
        this.esCommonsPathHome = esCommonsPathHome;
    }

    public String getEsCommonsPathData() {
        return esCommonsPathData;
    }

    public void setEsCommonsPathData(final String esCommonsPathData) {
        this.esCommonsPathData = esCommonsPathData;
    }

    public String getEsCommonsPathRepo() {
        return esCommonsPathRepo;
    }

    public void setEsCommonsPathRepo(final String esCommonsPathRepo) {
        this.esCommonsPathRepo = esCommonsPathRepo;
    }

    public String getEsCommonsHttpType() {
        return esCommonsHttpType;
    }

    public void setEsCommonsHttpType(final String esCommonsHttpType) {
        this.esCommonsHttpType = esCommonsHttpType;
    }

    public Integer getEsCommonsHttpPort() {
        return esCommonsHttpPort;
    }

    public void setEsCommonsHttpPort(final Integer esCommonsHttpPort) {
        this.esCommonsHttpPort = esCommonsHttpPort;
    }

    public Boolean getEsCommonsHttpEnabled() {
        return esCommonsHttpEnabled;
    }

    public void setEsCommonsHttpEnabled(final Boolean esCommonsHttpEnabled) {
        this.esCommonsHttpEnabled = esCommonsHttpEnabled;
    }

    public Integer getEsCommonsNodeMaxLocalStorageNodes() {
        return esCommonsNodeMaxLocalStorageNodes;
    }

    public void setEsCommonsNodeMaxLocalStorageNodes(final Integer esCommonsNodeMaxLocalStorageNodes) {
        this.esCommonsNodeMaxLocalStorageNodes = esCommonsNodeMaxLocalStorageNodes;
    }

    public Collection<Class<? extends Plugin>> getPlugins() {
        return plugins;
    }

    public void setPlugins(final Collection<Class<? extends Plugin>> plugins) {
        this.plugins = plugins;
    }
    //endregion

}
