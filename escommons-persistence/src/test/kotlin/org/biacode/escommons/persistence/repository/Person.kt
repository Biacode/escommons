package org.biacode.escommons.persistence.repository

import org.biacode.escommons.core.model.document.AbstractEsDocument

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 2:57 PM
 */
data class Person(var firstName: String = "") : AbstractEsDocument()