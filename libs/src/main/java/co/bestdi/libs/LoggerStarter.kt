package co.bestdi.libs

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import co.bestdi.libs.views.LoggerOverlay
import java.lang.ref.WeakReference

internal object LoggerStarter {
    private var weakLoggerOverlay: WeakReference<LoggerOverlay>? = null

    fun onAppBackgrounded() {
        hideScreenLogger()
    }

    fun onAppForegrounded(application: Application) {
        weakLoggerOverlay?.get()?.showScreenLogger()
                ?: addScreenLoggerOverlayOnWindow(application)
    }

    fun onAttachToApplication() {
        LoggerConstants.isLoggerEnabled = true
    }

    fun onDetachFromApplication(application: Application) {
        LoggerConstants.isLoggerEnabled = false
        weakLoggerOverlay?.get()?.detachScreenLogger(application)
        weakLoggerOverlay?.clear()
    }

    internal fun addScreenLog(screenLog: ScreenLog) {
        if (LoggerConstants.isLoggerEnabled) {
            LoggerRepository.addScreenLog(screenLog)
        }
    }

    private fun hideScreenLogger() {
        weakLoggerOverlay?.get()?.hideScreenLogger()
    }

    private fun addScreenLoggerOverlayOnWindow(application: Application) {
        val context = application.applicationContext
        // 对Android6.0 以上进行权限请求
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.packageName))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                Toast.makeText(context, context.getString(R.string.logger_request_permission_rationale), Toast.LENGTH_LONG).show()
            } else {
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                LoggerOverlay(context).run {
                    this.attachWindowManager(windowManager)
                    weakLoggerOverlay = WeakReference(this)
                }
            }
        } else {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            LoggerOverlay(context).run {
                this.attachWindowManager(windowManager)
                weakLoggerOverlay = WeakReference(this)
            }
        }
    }
}