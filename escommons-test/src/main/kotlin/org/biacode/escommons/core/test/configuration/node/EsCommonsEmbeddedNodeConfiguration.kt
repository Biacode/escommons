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
    var host: String? = null

    @Value("\${escommons.port}")
    var port: Int? = null

    @Value("\${escommons.cluster.name}")
    var esCommonsClusterName: String? = null

    @Value("\${escommons.path.home}")
    var esCommonsPathHome: String? = null

    @Value("\${escommons.path.data}")
    var esCommonsPathData: String? = null

    @Value("\${escommons.path.repo}")
    var esCommonsPathRepo: String? = null

    @Value("\${escommons.http.type}")
    var esCommonsHttpType: String? = null

    @Value("\${escommons.http.port}")
    var esCommonsHttpPort: Int? = null

    @Value("\${escommons.http.enabled}")
    var esCommonsHttpEnabled: Boolean? = null

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
                .put("http.type", esCommonsHttpType)
                .put("http.port", esCommonsHttpPort!!)
                .put("http.enabled", esCommonsHttpEnabled!!)
                .put("node.max_local_storage_nodes", esCommonsNodeMaxLocalStorageNodes!!)
                .build()
        val node = EsCommonsEmbeddedNode(settings, plugins)
        Runtime.getRuntime().addShutdownHook(Thread { IOUtils.closeQuietly(node) })
        return node.start().client()
    }
    //endregion

}
