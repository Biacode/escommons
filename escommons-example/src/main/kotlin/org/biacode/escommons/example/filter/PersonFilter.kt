package org.biacode.escommons.example.filter

/**
 * Created by Vasil Mamikonyan
 * Company: SFL LLC
 * Date: 4/3/2018
 * Time: 7:04 PM
 */
data class PersonFilter(
        val firstName: String,
        val from: Int,
        val size: Int
)