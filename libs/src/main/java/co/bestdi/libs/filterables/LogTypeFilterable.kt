package co.bestdi.libs.filterables

import co.bestdi.libs.LogType
import co.bestdi.libs.ScreenLog

internal class LogTypeFilterable(private val logType: LogType) : LogFilterable {
    override fun isFilterLog(screenLog: ScreenLog): Boolean {
        return when (logType) {
            LogType.ALL -> false
            else -> screenLog.logType != logType
        }
    }
}