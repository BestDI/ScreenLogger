package co.bestdi.screenlogger

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import co.bestdi.libs.LoggerLifecycleObserver

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.apply {
            addObserver(LoggerLifecycleObserver(this@MainApplication, true))
        }
    }

}