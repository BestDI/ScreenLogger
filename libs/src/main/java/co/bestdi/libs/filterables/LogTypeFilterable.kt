package co.bestdi.libs.filterables

import co.bestdi.libs.LoggerType
import co.bestdi.libs.ScreenLog

internal class LogTypeFilterable(private val loggerType: LoggerType) : LogFilterable {
    override fun isFilterLog(screenLog: ScreenLog): Boolean {
        return when (loggerType) {
            LoggerType.ALL -> false
            else -> screenLog.loggerType != loggerType
        }
    }
}