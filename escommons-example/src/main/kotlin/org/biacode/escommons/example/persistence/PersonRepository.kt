package org.biacode.escommons.example.persistence

import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.biacode.escommons.example.domain.Person
import org.biacode.escommons.example.filter.PersonFilter
import org.biacode.escommons.persistence.repository.EsRepository

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 5:23 PM
 */
interface PersonRepository : EsRepository<Person> {
    fun filter(filter: PersonFilter, indexName: String): DocumentsAndTotalCount<Person>
}