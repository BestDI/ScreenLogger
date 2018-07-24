package co.bestdi.libs.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import co.bestdi.libs.R
import co.bestdi.libs.ScreenLog
import co.bestdi.libs.ScreenLog.TraceLog

internal class LogItemView @JvmOverloads internal constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var tvLog: TextView

    init {
        initView()
        bindView()
    }

    private fun initView() {
        orientation = LinearLayout.VERTICAL
        LayoutInflater.from(context).inflate(R.layout.view_screen_log_item, this)
    }

    private fun bindView() {
        tvLog = findViewById(R.id.tvLog)
    }

    fun setData(screenLog: ScreenLog) {
        tvLog.text = screenLog.getReadableLogTitle()
        if (screenLog is TraceLog) {
            tvLog.setTextColor(context?.resources?.getColor(screenLog.loggerLevel.color)
                    ?: Color.parseColor("#FF1D23"))
        } else {
            tvLog.setTextColor(Color.WHITE)
        }
    }
}