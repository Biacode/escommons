package org.biacode.escommons.toolkit.component.impl

import org.biacode.escommons.toolkit.component.ClockComponent
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
@Component
class ClockComponentImpl : ClockComponent {

    //region Public methods
    override fun printCurrentDateTimeWithPredefinedFormatter(): String {
        return DATE_TIME_FORMATTER.format(LocalDateTime.now())
    }
    //endregion

    //region Companion objects
    companion object {
        private const val DATE_TIME_PATTERN = "yyyy_MM_dd_HH_mm_ss_SSS"
        private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
    }
    //endregion
}
