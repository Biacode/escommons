package org.biacode.escommons.example.controller

import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.biacode.escommons.example.domain.Person

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 5:25 PM
 */
interface PersonController {
    fun create(person: Person): Boolean

    fun filter(firstName: String): DocumentsAndTotalCount<Person>
}