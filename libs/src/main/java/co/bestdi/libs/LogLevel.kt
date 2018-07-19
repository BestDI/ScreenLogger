package co.bestdi.libs

enum class LogLevel {

    Trace {
        override val value: String
            get() = "[Trace] "
    },
    Debug {
        override val value: String
            get() = "[Debug] "
    },
    Info {
        override val value: String
            get() = "[Info] "
    },
    Warn {
        override val value: String
            get() = "[Warn] "
    },
    Error {
        override val value: String
            get() = "[Error] "
    };

    abstract val value: String
}