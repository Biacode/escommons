package org.biacode.escommons.example

import org.biacode.escommons.core.starter.annotation.EnableEsCommons
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

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