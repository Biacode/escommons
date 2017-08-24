package com.biacode.escommons.core.client.impl;

import com.biacode.escommons.core.client.EsClientBuilder;
import com.biacode.escommons.core.client.node.StandAloneNode;
import com.biacode.escommons.core.exception.EsCoreRuntimeException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Arthur Asatryan.
 * Date: 8/17/17
 * Time: 11:45 AM
 */
public class EsClientBuilderImpl implements EsClientBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsClientBuilderImpl.class);

    //region Constants
    private static final String TEST_ENVIRONMENT = "test";

    private static final String CLUSTER_NAME_SETTING_KEY = "cluster.name";
    private static final String CLUSTER_NAME_SETTING_VALUE = "netty4";
    private static final String TRANSPORT_TYPE_SETTING_KEY = "transport.type";
    private static final String TRANSPORT_TCP_PORT_SETTING_KEY = "transport.tcp.port";
    private static final String PATH_HOME_SETTING_KEY = "path.home";
    private static final String HTTP_ENABLED_SETTING_KEY = "http.enabled";
    //endregion

    //region Dependencies
    @Value("${escommons.host}")
    private String host;

    @Value("${escommons.port}")
    private Integer port;

    @Value("${escommons.cluster.name}")
    private String clusterName;

    @Value("${escommons.homepath}")
    private String homePath;

    @Value("${environment.type}")
    private String environmentType;

    private Collection<Class<? extends Plugin>> plugins;
    //endregion

    //region Constructors
    public EsClientBuilderImpl() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @Override
    public Client build() {
        if (Objects.equals(environmentType, TEST_ENVIRONMENT)) {
            return getTestClient();
        }
        return getProductionClient();
    }
    //endregion

    //region Utility methods
    private Client getTestClient() {
        final Settings nodeSettings = Settings.builder()
                .put(TRANSPORT_TYPE_SETTING_KEY, CLUSTER_NAME_SETTING_VALUE)
                .put(TRANSPORT_TCP_PORT_SETTING_KEY, port)
                .put(PATH_HOME_SETTING_KEY, homePath)
                .put(HTTP_ENABLED_SETTING_KEY, false)
                .build();
        try {
            return new StandAloneNode(
                    InternalSettingsPreparer.prepareEnvironment(nodeSettings, null),
                    plugins
            ).start().client();
        } catch (final NodeValidationException e) {
            LOGGER.error("Error occurs while initializing elastic search node at {}:{} - {}", host, port, e);
            throw new EsCoreRuntimeException("Error occurs while initializing elastic search node at " + host + ":" + port, e);
        }
    }
    //endregion

    private Client getProductionClient() {
        try {
            final Settings settings = Settings
                    .builder()
                    .put(CLUSTER_NAME_SETTING_KEY, clusterName)
                    .build();
            return new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (final UnknownHostException e) {
            LOGGER.error("Error occurs while initializing elastic search transport client for {}:{} - {}", host, port, e);
            throw new EsCoreRuntimeException("Error occurs while initializing elastic search transport client for " + host + ":" + port, e);
        }
    }

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

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(final String clusterName) {
        this.clusterName = clusterName;
    }

    public String getHomePath() {
        return homePath;
    }

    public void setHomePath(final String homePath) {
        this.homePath = homePath;
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(final String environmentType) {
        this.environmentType = environmentType;
    }

    public Collection<Class<? extends Plugin>> getPlugins() {
        return plugins;
    }

    public void setPlugins(final Collection<Class<? extends Plugin>> plugins) {
        this.plugins = plugins;
    }
    //endregion
}
