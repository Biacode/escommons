package org.biacode.escommons.example.controller.impl

import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.biacode.escommons.example.controller.PersonController
import org.biacode.escommons.example.domain.Person
import org.biacode.escommons.example.filter.PersonFilter
import org.biacode.escommons.example.service.PersonService
import org.springframework.web.bind.annotation.*

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 5:25 PM
 */
@RestController
@RequestMapping("persons")
class PersonControllerImpl(val personService: PersonService) : PersonController {

    @PostMapping("create")
    override fun create(@RequestBody person: Person): Boolean {
        return personService.save(person, PERSON_INDEX)
    }

    @GetMapping("filter")
    override fun filter(@RequestParam("firstName") firstName: String): DocumentsAndTotalCount<Person> {
        return personService.getByFilter(PersonFilter(firstName, 0, 10), PERSON_INDEX)
    }

    companion object {
        const val PERSON_INDEX: String = "person_index"
    }
}