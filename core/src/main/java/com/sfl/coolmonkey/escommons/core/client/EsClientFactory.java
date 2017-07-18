package com.sfl.coolmonkey.escommons.core.client;

import com.sfl.coolmonkey.escommons.core.client.node.StandAloneNode;
import com.sfl.coolmonkey.escommons.core.exception.EsCoreRuntimeException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.transport.Netty4Plugin;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@SuppressWarnings({"squid:S2387", "squid:S1845"})
@Configuration("esClient")
public class EsClientFactory extends AbstractFactoryBean<Client> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsClientFactory.class);

    //region Constants
    private static final String TEST_ENVIRONMENT = "test";

    private static final String CLUSTER_NAME = "cluster.name";
    //endregion

    //region Dependencies
    @Value("#{appProperties['elasticsearch.host']}")
    private String host;

    @Value("#{appProperties['elasticsearch.port']}")
    private Integer port;

    @Value("#{appProperties['elasticsearch.cluster.name']}")
    private String clusterName;

    @Value("#{appProperties['elasticsearch.homepath']}")
    private String homePath;

    @Value("#{appProperties['environment.type']}")
    private String environmentType;
    //endregion

    //region Constructors
    public EsClientFactory() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @Override
    public Class<?> getObjectType() {
        return Client.class;
    }

    @SuppressWarnings({"squid:S2095"})
    @Override
    protected Client createInstance() {
        if (Objects.equals(environmentType, TEST_ENVIRONMENT)) {
            final Settings nodeSettings = Settings.builder()
                    .put("transport.type", "netty4")
                    .put("transport.tcp.port", port)
                    .put("path.home", homePath)
                    .put("http.enabled", false)
                    .build();
            final StandAloneNode node = new StandAloneNode(
                    InternalSettingsPreparer.prepareEnvironment(nodeSettings, null),
                    Collections.singletonList(Netty4Plugin.class)
            );
            try {
                node.start();
            } catch (final NodeValidationException e) {
                LOGGER.error("Error occurs while initializing elastic search node at {}:{}", host, port, e);
                throw new EsCoreRuntimeException("Error occurs while initializing elastic search node at " + host + ":" + port, e);
            }
        }
        try {
            final Settings settings = Settings
                    .builder()
                    .put(CLUSTER_NAME, clusterName)
                    .build();
            return new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (final UnknownHostException e) {
            LOGGER.error("Error occurs while initializing elastic search transport client for {}:{}", host, port, e);
            throw new EsCoreRuntimeException("Error occurs while initializing elastic search transport client for " + host + ":" + port, e);
        }
    }
    //endregion
}
