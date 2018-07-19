package co.bestdi.libs

internal enum class LoggerType {
    ALL,
    API,
    TRACKING;

    fun getTitleRes(): Int {
        return when (this) {
            ALL -> R.string.logger_log_type_all
            API -> R.string.logger_log_type_api
            TRACKING -> R.string.logger_log_type_tracking
        }
    }

    companion object {
        fun valuesOf(position: Int): LoggerType? {
            val values = LoggerType.values()
            if (position < 0 || position > values.size) return null
            return values[position]
        }
    }
}