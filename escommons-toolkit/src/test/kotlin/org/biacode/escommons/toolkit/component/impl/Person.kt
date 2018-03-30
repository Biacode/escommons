package org.biacode.escommons.toolkit.component.impl

import com.fasterxml.jackson.annotation.JsonProperty
import org.biacode.escommons.core.model.document.AbstractEsDocument

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 2:57 PM
 */
data class Person(@JsonProperty("firstName") var firstName: String) : AbstractEsDocument()
