package org.biacode.escommons.core.test.configuration.node

import org.apache.commons.io.IOUtils
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.plugins.Plugin
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 3/23/18
 * Time: 12:08 PM
 */
@Configuration
class EsCommonsEmbeddedNodeConfiguration {

    //region Dependencies
    @Value("\${escommons.host}")
    lateinit var esCommonsHost: String

    @Value("\${escommons.port}")
    var esCommonsPort: Int? = null

    @Value("\${escommons.cluster.name}")
    lateinit var esCommonsClusterName: String

    @Value("\${escommons.path.home}")
    lateinit var esCommonsPathHome: String

    @Value("\${escommons.path.data}")
    lateinit var esCommonsPathData: String

    @Value("\${escommons.path.repo}")
    lateinit var esCommonsPathRepo: String

    @Value("\${escommons.http.type}")
    lateinit var esCommonsHttpType: String

    @Value("\${escommons.http.host}")
    lateinit var esCommonsHttpHost: String

    @Value("\${escommons.http.port}")
    var esCommonsHttpPort: Int? = null

    @Value("\${escommons.node.max_local_storage_nodes}")
    var esCommonsNodeMaxLocalStorageNodes: Int? = null

    @Value("\${escommons.plugins}")
    var plugins: Collection<Class<out Plugin>>? = null
    //endregion

    //region Public methods
    @Bean
    fun embeddedServer(): Client {
        val settings = Settings.builder()
                .put("cluster.name", esCommonsClusterName)
                .put("path.home", esCommonsPathHome)
                .put("path.data", esCommonsPathData)
                .put("path.repo", esCommonsPathRepo)
                .put("network.host", esCommonsHost)
                .put("transport.tcp.port", esCommonsPort!!)
                .put("http.type", esCommonsHttpType)
                .put("http.host", esCommonsHttpHost)
                .put("http.port", esCommonsHttpPort!!)
                .put("node.max_local_storage_nodes", esCommonsNodeMaxLocalStorageNodes!!)
                .build()
        val node = EsCommonsEmbeddedNode(settings, plugins)
        Runtime.getRuntime().addShutdownHook(Thread { IOUtils.closeQuietly(node) })
        return node.start().client()
    }
    //endregion

}
