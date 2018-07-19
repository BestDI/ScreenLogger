package co.bestdi.libs

object Logger {

    @JvmStatic
    fun trace(tag: String, text: String) {
        log(tag, text, LoggerLevel.Trace)
    }

    @JvmStatic
    fun debug(tag: String, text: String) {
        log(tag, text, LoggerLevel.Debug)
    }

    @JvmStatic
    fun info(tag: String, text: String) {
        log(tag, text, LoggerLevel.Info)
    }

    @JvmStatic
    fun warn(tag: String, text: String) {
        log(tag, text, LoggerLevel.Warn)
    }

    @JvmStatic
    fun error(tag: String, text: String) {
        log(tag, text, LoggerLevel.Error)
    }

    private fun log(tag: String, text: String, loggerLevel: LoggerLevel) {
        if (LoggerConstants.isLoggerEnabled) {
            ScreenLog.TraceLog(
                    tag,
                    text,
                    loggerLevel
            ).apply {
                LoggerStarter.addScreenLog(this)
            }
        }
    }

    @JvmStatic
    fun request(requestTitle: String, requestDetails: String) {
        if (LoggerConstants.isLoggerEnabled) {
            ScreenLog.RequestLog(
                    requestTitle,
                    requestDetails
            ).apply {
                LoggerStarter.addScreenLog(this)
            }
        }
    }

    @JvmStatic
    fun response(readableID: String, responseDetails: String, isSuccessResponse: Boolean) {
        if (LoggerConstants.isLoggerEnabled) {
            ScreenLog.ResponseLog(
                    readableID,
                    responseDetails,
                    isSuccessResponse
            ).apply {
                LoggerStarter.addScreenLog(this)
            }
        }
    }
}