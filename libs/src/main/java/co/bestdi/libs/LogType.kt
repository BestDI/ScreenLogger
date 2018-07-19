package co.bestdi.libs

enum class LogType {
    ALL,
    API,
    TRACKING;

    fun getTitleRes(): Int {
        return when (this) {
            ALL -> R.string.screen_logger_log_type_all
            API -> R.string.screen_logger_log_type_api
            TRACKING -> R.string.screen_logger_log_type_tracking
        }
    }

    companion object {
        fun valuesOf(position: Int): LogType? {
            val values = LogType.values()
            if (position < 0 || position > values.size) return null
            return values[position]
        }
    }
}