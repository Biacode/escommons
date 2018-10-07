package org.biacode.escommons.toolkit.component

import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 3/27/18
 * Time: 5:07 PM
 */
interface ResourceReaderComponent {
    fun asString(path: String): Optional<String>
}