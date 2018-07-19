package co.bestdi.libs

object LoggerRepository {
    private val screenLogs: MutableList<ScreenLog> = arrayListOf()
    private val screenLogObservers: MutableList<ScreenLogDataObserver> = arrayListOf()

    internal fun addScreenLog(screenLog: ScreenLog) {
        screenLogs.add(screenLog)
        screenLogObservers.forEach { it.onScreenLogAdded(cloneOf(screenLog)) }
    }

    internal fun addScreenLogs(screenLogs: Collection<ScreenLog>) {
        LoggerRepository.screenLogs.addAll(screenLogs)
        val clone = cloneOf(screenLogs)
        screenLogObservers.forEach { it.onScreenLogsAdded(clone) }
    }

    internal fun addScreenLogObserver(screenLogDataObserver: ScreenLogDataObserver) {
        screenLogObservers.add(screenLogDataObserver)
        screenLogDataObserver.onObserverAdded(cloneOf(screenLogs))
    }

    internal fun clearAllLogs() {
        screenLogs.clear()
        screenLogObservers.forEach { it.onAllLogsCleared() }
    }

    private fun cloneOf(screenLogs: Collection<ScreenLog>): Collection<ScreenLog> {
        return arrayListOf<ScreenLog>().apply {
            addAll(screenLogs)
        }
    }

    private fun cloneOf(screenLog: ScreenLog): ScreenLog {
        return screenLog.clone()
    }

    internal interface ScreenLogDataObserver {
        fun onObserverAdded(initialScreenLogs: Collection<ScreenLog>)

        fun onScreenLogAdded(screenLog: ScreenLog)

        fun onScreenLogsAdded(screenLogs: Collection<ScreenLog>)

        fun onAllLogsCleared()
    }
}