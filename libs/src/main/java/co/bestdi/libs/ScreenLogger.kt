package co.bestdi.libs

object ScreenLogger {

    @JvmStatic
    fun trace(tag: String, text: String) {
        log(tag, text, LogLevel.Trace)
    }

    @JvmStatic
    fun debug(tag: String, text: String) {
        log(tag, text, LogLevel.Debug)
    }

    @JvmStatic
    fun info(tag: String, text: String) {
        log(tag, text, LogLevel.Info)
    }

    @JvmStatic
    fun warn(tag: String, text: String) {
        log(tag, text, LogLevel.Warn)
    }

    @JvmStatic
    fun error(tag: String, text: String) {
        log(tag, text, LogLevel.Error)
    }

    private fun log(tag: String, text: String, logLevel: LogLevel) {
        if (ScreenLoggerConstants.isLoggerEnabled) {
            ScreenLog.TraceLog(
                    tag,
                    text,
                    logLevel
            ).apply {
                ScreenLoggerStarter.addScreenLog(this)
            }
        }
    }

    @JvmStatic
    fun sendRequest(requestTitle: String, requestDetails: String) {
        if (ScreenLoggerConstants.isLoggerEnabled) {
            ScreenLog.RequestLog(
                    requestTitle,
                    requestDetails
            ).apply {
                ScreenLoggerStarter.addScreenLog(this)
            }
        }
    }

    @JvmStatic
    fun sendResponse(readableID: String, responseDetails: String, isSuccessResponse: Boolean) {
        if (ScreenLoggerConstants.isLoggerEnabled) {
            ScreenLog.ResponseLog(
                    readableID,
                    responseDetails,
                    isSuccessResponse
            ).apply {
                ScreenLoggerStarter.addScreenLog(this)
            }
        }
    }
}