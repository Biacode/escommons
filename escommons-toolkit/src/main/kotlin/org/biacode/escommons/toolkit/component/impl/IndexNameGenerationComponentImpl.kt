package org.biacode.escommons.toolkit.component.impl

import org.biacode.escommons.toolkit.component.ClockComponent
import org.biacode.escommons.toolkit.component.IndexNameGenerationComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
class IndexNameGenerationComponentImpl : IndexNameGenerationComponent {

    //region Dependencies
    @Autowired
    private lateinit var clockComponent: ClockComponent
    //endregion

    //region Public methods
    override fun generate(aliasName: String): String {
        return "${aliasName}_date_${clockComponent.printCurrentDateTimeWithPredefinedFormatter()}_uuid_${UUID.randomUUID()}"
    }
    //endregion
}
