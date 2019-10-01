package org.biacode.escommons.example

import org.biacode.escommons.core.starter.annotation.EnableEsCommons
import org.biacode.escommons.example.controller.impl.PersonControllerImpl.Companion.PERSON_INDEX
import org.biacode.escommons.example.domain.Person
import org.biacode.escommons.example.service.PersonService
import org.biacode.escommons.toolkit.component.EsCommonsClientWrapper
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 4:25 PM
 */
@EnableEsCommons
@SpringBootApplication
class MainApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(MainApplication::class.java)
        }
    }
}

@Component
class PersonDemoData(val personService: PersonService, val esCommonsClientWrapper: EsCommonsClientWrapper) : CommandLineRunner {
    override fun run(vararg args: String) {
        esCommonsClientWrapper.deleteIndices(PERSON_INDEX)
        arrayOf(
                Person("Arthur", "Asatryan", 100),
                Person("Darth", "Vader", 101),
                Person("Anakin", "Skywalker", 1010)
        ).forEach { personService.save(it, PERSON_INDEX) }
    }

}