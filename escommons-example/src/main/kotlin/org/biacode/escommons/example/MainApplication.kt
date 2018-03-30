package org.biacode.escommons.example

import org.biacode.escommons.core.starter.annotation.EnableEsCommons
import org.biacode.escommons.core.test.configuration.EsCommonsTestAnnotationDrivenConfiguration
import org.biacode.escommons.toolkit.component.EsCommonsClientWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 4:25 PM
 */
@EnableAutoConfiguration
@EnableEsCommons
@SpringBootApplication
@ComponentScan("org.biacode.escommons.example")
class MainApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(MainApplication::class.java)
        }
    }
}

@Configuration
@Import(EsCommonsTestAnnotationDrivenConfiguration::class)
class MainApplicationCommandLineRunner : CommandLineRunner {

    //region Dependencies
    @Autowired
    private lateinit var esCommonsClientWrapper: EsCommonsClientWrapper
    //endregion

    //region Public methods
    override fun run(vararg p0: String?) {
        esCommonsClientWrapper.deleteIndices("*")
        val indexName = UUID.randomUUID().toString()
        esCommonsClientWrapper.createIndex(indexName, "person", "person")
        esCommonsClientWrapper.addAlias(indexName, "person")
        esCommonsClientWrapper.getIndices().forEach { println(it) }
    }
    //endregion

}