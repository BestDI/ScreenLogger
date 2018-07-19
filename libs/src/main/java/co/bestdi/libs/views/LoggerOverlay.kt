package co.bestdi.libs.views

import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import co.bestdi.libs.R

internal class LoggerOverlay @JvmOverloads internal constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    companion object {
        const val screenLoggerDefaultVisible = false
    }

    private lateinit var tvVersion: TextView
    private lateinit var titleView: LoggerTitleView
    private lateinit var flScreenLoggerContainer: View
    private lateinit var cbScreenLogger: CheckBox
    private lateinit var vpcLogger: LoggerViewPagerContainer
    private lateinit var btnClose: Button
    private var isScreenLoggerVisible: Boolean = screenLoggerDefaultVisible
        set(value) {
            if (field != value) {
                field = value
                if (value) {
                    visibility = View.VISIBLE
                } else {
                    visibility = View.GONE
                }
            }
        }
    private var isScreenLoggerAttached = false

    init {
        initView()
        bindView()
        setupView()
    }

    private fun initView() {
        orientation = LinearLayout.VERTICAL
        visibility = if (screenLoggerDefaultVisible) View.VISIBLE else View.GONE
        LayoutInflater.from(context).inflate(R.layout.view_screen_logger_overlay, this)
        setBackgroundColor(ContextCompat.getColor(context, R.color.loggerBackground))
    }

    private fun bindView() {
        tvVersion = findViewById(R.id.tvVersion)
        titleView = findViewById(R.id.titleView)
        vpcLogger = findViewById(R.id.vpcScreenLogger)
        btnClose = findViewById(R.id.btnClose)
    }

    private fun setupView() {
        context.packageManager.getPackageInfo(context.packageName, 0).apply {
            tvVersion.text = "Version: $versionName($versionCode)"
        }
        flScreenLoggerContainer = LayoutInflater.from(context).inflate(R.layout.view_screen_logger_checkbox_container, null)
        cbScreenLogger = flScreenLoggerContainer.findViewById(R.id.cbScreenLogger)
        cbScreenLogger.setOnCheckedChangeListener { buttonView, isChecked ->
            isScreenLoggerVisible = isChecked
        }
        vpcLogger.onTitleChangeListener = object : LoggerViewPager.OnTitleChangeListener {
            override fun onTitleChanged(title: String?, leftButtonType: LoggerTitleView.LeftButtonType) {
                updateTitleView(title, leftButtonType)
            }
        }
        btnClose.setOnClickListener { cbScreenLogger.isChecked = false }
        titleView.onTitleViewClickListener = object : LoggerTitleView.OnTitleViewClickListener {
            override fun onLeftBtnClicked() {
                vpcLogger.showLogList()
            }

            override fun onTitleClicked() {
            }
        }
    }

    fun attachWindowManager(windowManager: WindowManager) {
        attachThisViewToWindowManager(windowManager)
        attachCheckboxToWindowManager(windowManager)
        isScreenLoggerAttached = true
    }

    fun showScreenLogger() {
        if (isScreenLoggerAttached) {
            flScreenLoggerContainer.visibility = View.VISIBLE
            if (cbScreenLogger.isChecked) {
                visibility = View.VISIBLE
            }
        }
    }

    fun hideScreenLogger() {
        if (isScreenLoggerAttached) {
            flScreenLoggerContainer.visibility = View.GONE
            visibility = View.GONE
        }
    }

    fun detachScreenLogger(application: Application) {
        val windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.removeView(cbScreenLogger)
        windowManager.removeView(this)
        isScreenLoggerAttached = false
    }

    private fun attachThisViewToWindowManager(windowManager: WindowManager) {
        with(WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                getWindowLayoutParamType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)) {
            windowManager.addView(this@LoggerOverlay, this)
        }
    }

    private fun attachCheckboxToWindowManager(windowManager: WindowManager) {
        with(WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getWindowLayoutParamType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)) {
            gravity = Gravity.RIGHT or Gravity.BOTTOM
            windowManager.addView(flScreenLoggerContainer, this)
        }
    }

    private fun getWindowLayoutParamType(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            WindowManager.LayoutParams.TYPE_PHONE
        } else {
            WindowManager.LayoutParams.TYPE_TOAST
        }
    }

    private fun updateTitleView(title: String?, leftButtonType: LoggerTitleView.LeftButtonType) {
        titleView.titleText = title
        titleView.leftButtonType = leftButtonType
    }
}