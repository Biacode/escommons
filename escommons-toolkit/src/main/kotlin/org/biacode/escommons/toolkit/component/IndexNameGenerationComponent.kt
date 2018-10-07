package org.biacode.escommons.toolkit.component

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
interface IndexNameGenerationComponent {
    fun generate(aliasName: String): String
}
