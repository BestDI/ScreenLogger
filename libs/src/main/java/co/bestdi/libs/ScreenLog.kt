package co.bestdi.libs

import java.text.SimpleDateFormat
import java.util.*

/**
 * ScreenLog APILog/TrackingLog
 */
internal sealed class ScreenLog(protected val timeInMillis: Long) : Comparable<ScreenLog> {
    companion object {
        private const val GENERAL_SCREEN_LOG_TIMESTAMP_FORMAT = "MM-dd HH:mm:ss"
    }

    // 用以区分Log的前后顺序
    override fun compareTo(other: ScreenLog): Int = (timeInMillis - other.timeInMillis).toInt()

    abstract fun getLogDetail(): String?

    abstract val loggerType: LoggerType

    abstract fun clone(): ScreenLog

    protected abstract val logTitle: String

    fun getReadableLogTitle(): String {
        val formattedTimestamp = SimpleDateFormat(GENERAL_SCREEN_LOG_TIMESTAMP_FORMAT, Locale.getDefault()).format(timeInMillis)
        return "$logTitle ($formattedTimestamp)"
    }


    // Sealed Class
    abstract class APILog(timeInMillis: Long = System.currentTimeMillis()) : ScreenLog(timeInMillis) {
        final override val loggerType: LoggerType = LoggerType.API
    }

    class RequestLog(
            private val readableID: String,
            private val logDetails: String? = null,
            timeInMillis: Long = System.currentTimeMillis()
    ) : APILog(timeInMillis) {
        override fun getLogDetail(): String? = logDetails
        override val logTitle: String
            get() = "---> REQUEST : $readableID"

        override fun clone() = RequestLog(readableID, logDetails, timeInMillis)
    }

    class ResponseLog(
            private val readableID: String,
            private val logDetails: String? = null,
            private val isSuccessResponse: Boolean,
            timeInMillis: Long = System.currentTimeMillis()
    ) : APILog(timeInMillis) {
        override fun getLogDetail(): String? = logDetails

        override val logTitle: String
            get() = "RESPONSE <--- : $readableID - ${isSuccessString(isSuccessResponse)}"

        private fun isSuccessString(isSuccessResponse: Boolean): String = if (isSuccessResponse) "Successful" else "Failed"

        override fun clone(): ScreenLog {
            return ResponseLog(readableID, logDetails, isSuccessResponse, timeInMillis)
        }
    }

    // Sealed Class - TraceLog
    class TraceLog(private val tag: String,
                   private val text: String? = null,
                   val loggerLevel: LoggerLevel,
                   timeInMillis: Long = System.currentTimeMillis()
    ) : ScreenLog(timeInMillis) {
        override val loggerType: LoggerType
            get() = LoggerType.TRACKING

        override fun getLogDetail(): String? = text

        override fun clone() = TraceLog(tag, text, loggerLevel, timeInMillis)

        override val logTitle: String
            get() = loggerLevel.value + tag

    }
}