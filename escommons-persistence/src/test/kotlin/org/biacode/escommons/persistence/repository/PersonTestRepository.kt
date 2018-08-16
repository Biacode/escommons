package org.biacode.escommons.persistence.repository

import org.biacode.escommons.persistence.repository.impl.AbstractEsRepository
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 3/30/18
 * Time: 4:51 PM
 */
@Component
class PersonTestRepository : AbstractEsRepository<Person>() {

    //region Dependencies
    @Autowired
    private lateinit var esClient: RestHighLevelClient
    //endregion
}