package co.bestdi.screenlogger

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import co.bestdi.libs.ScreenLoggerLifecycleObserver

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.apply {
            addObserver(ScreenLoggerLifecycleObserver(this@MainApplication, true))
        }
    }

}