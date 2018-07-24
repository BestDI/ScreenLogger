package co.bestdi.libs

/**
 * 定义Logger标签,颜色
 */
internal enum class LoggerLevel {

    Trace {
        override val color: Int
            get() = android.R.color.white
        override val value: String
            get() = "[Trace] "
    },
    Debug {
        override val color: Int
            get() = android.R.color.holo_green_light
        override val value: String
            get() = "[Debug] "
    },
    Info {
        override val color: Int
            get() = android.R.color.holo_green_dark
        override val value: String
            get() = "[Info] "
    },
    Warn {
        override val color: Int
            get() = R.color.warnLogColor
        override val value: String
            get() = "[Warn] "
    },
    Error {
        override val color: Int
            get() = R.color.errorLogColor
        override val value: String
            get() = "[Error] "
    };

    abstract val value: String
    abstract val color: Int
}