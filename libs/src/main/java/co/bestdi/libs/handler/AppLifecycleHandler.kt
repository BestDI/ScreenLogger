package co.bestdi.libs.handler

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Bundle
import java.lang.ref.WeakReference

internal class AppLifecycleHandler private constructor(
        application: Application
) :
        ComponentCallbacks2,
        Application.ActivityLifecycleCallbacks {
    companion object {

        fun withApplication(application: Application): AppLifecycleHandler {
            return AppLifecycleHandler(application).apply {
                registerFromApplication()
            }
        }
    }

    private val lifecycleDelegates: MutableList<LifecycleDelegate> = arrayListOf()
    private val weakApplication: WeakReference<Application> = WeakReference(application)

    var isAppBackgrounded: Boolean = false
        private set

    override fun onLowMemory() {
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
    }

    override fun onTrimMemory(level: Int) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            isAppBackgrounded = true
            // lifecycleDelegate instance was passed in on the constructor
            notifyLifecycleDelegates()
        }
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
        if (isAppBackgrounded) {
            isAppBackgrounded = false
            notifyLifecycleDelegates()
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }

    fun addLifecycleDelegate(lifecycleDelegate: LifecycleDelegate) {
        lifecycleDelegates.add(lifecycleDelegate)
        notifyLifecycleDelegates()
    }

    private fun registerFromApplication() {
        with(weakApplication.get()) {
            this?.registerActivityLifecycleCallbacks(this@AppLifecycleHandler)
            this?.registerComponentCallbacks(this@AppLifecycleHandler)
        }
    }

    fun removeLifecycleDelegate(lifecycleDelegate: LifecycleDelegate) {
        if (lifecycleDelegates.contains(lifecycleDelegate)) {
            lifecycleDelegates.remove(lifecycleDelegate)
        }
    }

    fun onDetachFromApplication() {
        lifecycleDelegates.clear()
        with(weakApplication.get()) {
            this?.unregisterActivityLifecycleCallbacks(this@AppLifecycleHandler)
            this?.unregisterComponentCallbacks(this@AppLifecycleHandler)
        }
        weakApplication.clear()
    }

    private fun notifyLifecycleDelegates() {
        if (isAppBackgrounded) {
            notifyLifecycleDelegatesOnAppBackgrounded()
        } else {
            notifyLifecycleDelegatesOnAppForegrounded()
        }
    }

    private fun notifyLifecycleDelegatesOnAppBackgrounded() {
        lifecycleDelegates.forEach { it.onAppBackgrounded() }
    }

    private fun notifyLifecycleDelegatesOnAppForegrounded() {
        weakApplication.get()?.let {
            val application = it
            lifecycleDelegates.forEach { it.onAppForegrounded(application) }
        }
    }

    interface LifecycleDelegate {
        fun onAppBackgrounded()

        fun onAppForegrounded(application: Application)
    }
}