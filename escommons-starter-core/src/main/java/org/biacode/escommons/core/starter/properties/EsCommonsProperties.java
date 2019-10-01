package org.biacode.escommons.core.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Created by Arthur Asatryan.
 * Date: 3/28/18
 * Time: 3:41 PM
 * <p>
 * escommons.http.host=elasticsearch
 * escommons.http.port=9200
 * escommons.http.type=netty4
 * escommons.host=localhost
 * escommons.port=9372
 * escommons.scroll.size=100
 * escommons.cluster.name=escommons
 * escommons.path.home=/tmp/escommons/home
 * escommons.path.data=/tmp/escommons/data
 * escommons.path.repo=/tmp/escommons/repo
 * escommons.node.max_local_storage_nodes=10
 * escommons.plugins=org.elasticsearch.transport.Netty4Plugin
 */
@Configuration
@ConfigurationProperties(prefix = "escommons")
public class EsCommonsProperties {

    /**
     * The embedded node host
     */
    private String host = "localhost";

    /**
     * The embedded node port
     */
    private Integer port = 9372;

    /**
     * The embedded node plugins
     */
    private List<String> plugins = singletonList("org.elasticsearch.transport.Netty4Plugin");

    /**
     * HTTP properties
     */
    private Http http = new Http();

    /**
     * Scroll properties
     */
    private Scroll scroll = new Scroll();

    /**
     * Cluster properties
     */
    private Cluster cluster = new Cluster();

    /**
     * Path properties
     */
    private Path path = new Path();

    /**
     * Node properties
     */
    private Node node = new Node();

    private static class Http {
        /**
         * HTTP Host
         */
        private String host = "elasticsearch";

        /**
         * HTTP Port
         */
        private Integer port = 9200;

        /**
         * HTTP Type
         */
        private String type = "netty4";

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

        public String getType() {
            return type;
        }

        public void setType(final String type) {
            this.type = type;
        }
    }

    private static class Path {
        /**
         * The home path
         */
        private String home = "/tmp/escommons/home";

        /**
         * The data path
         */
        private String data = "/tmp/escommons/data";

        /**
         * The repo path
         */
        private String repo = "/tmp/escommons/repo";

        public String getHome() {
            return home;
        }

        public void setHome(final String home) {
            this.home = home;
        }

        public String getData() {
            return data;
        }

        public void setData(final String data) {
            this.data = data;
        }

        public String getRepo() {
            return repo;
        }

        public void setRepo(final String repo) {
            this.repo = repo;
        }
    }

    private static class Node {
        /**
         * Max local storage nodes
         */
        private Integer maxLocalStorageNodes = 10;

        public Integer getMaxLocalStorageNodes() {
            return maxLocalStorageNodes;
        }

        public void setMaxLocalStorageNodes(final Integer maxLocalStorageNodes) {
            this.maxLocalStorageNodes = maxLocalStorageNodes;
        }
    }

    private static class Cluster {
        /**
         * Cluster name
         */
        private String name = "escommons";

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }

    private static class Scroll {
        /**
         * The size of scroll chunk
         */
        private Integer size = 100;

        public Integer getSize() {
            return size;
        }

        public void setSize(final Integer size) {
            this.size = size;
        }
    }

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

    public List<String> getPlugins() {
        return plugins;
    }

    public void setPlugins(final List<String> plugins) {
        this.plugins = plugins;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(final Http http) {
        this.http = http;
    }

    public Scroll getScroll() {
        return scroll;
    }

    public void setScroll(final Scroll scroll) {
        this.scroll = scroll;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(final Cluster cluster) {
        this.cluster = cluster;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(final Path path) {
        this.path = path;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(final Node node) {
        this.node = node;
    }
}