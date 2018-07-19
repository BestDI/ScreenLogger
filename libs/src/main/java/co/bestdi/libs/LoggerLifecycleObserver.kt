package co.bestdi.libs

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

class LoggerLifecycleObserver(private val application: Application,
                              private val isEnable: Boolean) : LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (isEnable) {
            LoggerStarter.onAttachToApplication()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        if (isEnable) {
            LoggerStarter.onAppForegrounded(application)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        if (isEnable) {
            LoggerStarter.onAppBackgrounded()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (isEnable) {
            LoggerStarter.onDetachFromApplication(application)
        }
    }
}