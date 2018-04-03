package org.biacode.escommons.example.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 5:21 PM
 */
data class Person(
        @JsonProperty("firstName")
        val firstName: String,

        @JsonProperty("lastName")
        val lastName: String,

        @JsonProperty("age")
        val age: Int
)