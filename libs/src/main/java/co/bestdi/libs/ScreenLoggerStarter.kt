package co.bestdi.libs

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import co.bestdi.libs.views.ScreenLoggerOverlay
import java.lang.ref.WeakReference

internal object ScreenLoggerStarter {
    private var weakScreenLoggerOverlay: WeakReference<ScreenLoggerOverlay>? = null

    fun onAppBackgrounded() {
        hideScreenLogger()
    }

    fun onAppForegrounded(application: Application) {
        weakScreenLoggerOverlay?.get()?.showScreenLogger()
                ?: addScreenLoggerOverlayOnWindow(application)
    }

    fun onAttachToApplication() {
        ScreenLoggerConstants.isLoggerEnabled = true
    }

    fun onDetachFromApplication(application: Application) {
        ScreenLoggerConstants.isLoggerEnabled = false
        weakScreenLoggerOverlay?.get()?.detachScreenLogger(application)
        weakScreenLoggerOverlay?.clear()
    }

    internal fun addScreenLog(screenLog: ScreenLog) {
        if (ScreenLoggerConstants.isLoggerEnabled) {
            ScreenLogRepository.addScreenLog(screenLog)
        }
    }

    private fun hideScreenLogger() {
        weakScreenLoggerOverlay?.get()?.hideScreenLogger()
    }

    private fun addScreenLoggerOverlayOnWindow(application: Application) {
        val context = application.applicationContext
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.packageName))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                Toast.makeText(context, context.getString(R.string.screen_logger_request_permission_rationale), Toast.LENGTH_LONG).show()
            } else {
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                ScreenLoggerOverlay(context).apply {
                    this.attachWindowManager(windowManager)
                    weakScreenLoggerOverlay = WeakReference<ScreenLoggerOverlay>(this)
                }
            }
        } else {
            // Added By Mia. For Version lower than 23. show overlay directly. 2018/06/25
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ScreenLoggerOverlay(context).apply {
                this.attachWindowManager(windowManager)
                weakScreenLoggerOverlay = WeakReference(this)
            }
        }
    }
}