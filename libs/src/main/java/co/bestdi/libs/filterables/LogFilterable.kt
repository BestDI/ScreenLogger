package co.bestdi.libs.filterables

import co.bestdi.libs.ScreenLog

internal interface LogFilterable {
    fun isFilterLog(screenLog: ScreenLog): Boolean
}