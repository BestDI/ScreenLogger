package co.bestdi.libs.views

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import co.bestdi.libs.R
import co.bestdi.libs.ScreenLog
import co.bestdi.libs.ScreenLogRepository
import co.bestdi.libs.adapters.ScreenLoggerViewPagerAdapter

internal class ScreenLoggerViewPagerContainer @JvmOverloads internal constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private lateinit var vpScreenLogger: ScreenLoggerViewPager
    private lateinit var tvLogDetails: TextView
    private lateinit var btnClearAllLogs: Button
    private lateinit var llContainer: ViewGroup
    private val screenLoggerAdapter: ScreenLoggerViewPagerAdapter by lazy {
        context?.let { ScreenLoggerViewPagerAdapter(it) } ?: error("context should not be null")
    }

    var onTitleChangeListener: ScreenLoggerViewPager.OnTitleChangeListener? = null

    init {
        initView()
        bindView()
        setupView()
    }

    fun showLogList() {
        llContainer.visibility = View.VISIBLE
        tvLogDetails.visibility = View.GONE
        updateTitleViewForLogList()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_screen_logger_view_pager_container, this)
    }

    private fun bindView() {
        vpScreenLogger = findViewById(R.id.vpScreenLogger)
        tvLogDetails = findViewById(R.id.tvLogDetails)
        btnClearAllLogs = findViewById(R.id.btnClearAllLogs)
        llContainer = findViewById(R.id.llContainer)
    }

    private fun setupView() {
        btnClearAllLogs.setOnClickListener { clearAllLogs() }
        tvLogDetails.movementMethod = ScrollingMovementMethod()
        vpScreenLogger.adapter = screenLoggerAdapter.apply {
            this.onCellClickListener = object : ScreenLogListContainer.OnCellClickListener {
                override fun onCellClicked(screenLog: ScreenLog) {
                    showLogDetails(screenLog)
                    updateTitleViewForLogDetails(screenLog)
                }
            }
        }
        vpScreenLogger.onTitleChangeListener = object : ScreenLoggerViewPager.OnTitleChangeListener {
            override fun onTitleChanged(title: String?, leftButtonType: ScreenLoggerTitleView.LeftButtonType) {
                onTitleChangeListener?.onTitleChanged(title, leftButtonType)
            }
        }
        vpScreenLogger.currentItem = 0
    }

    private fun updateTitleViewForLogList() {
        vpScreenLogger.adapter?.let {
            val title = it.getPageTitle(vpScreenLogger.currentItem).toString()
            onTitleChangeListener?.onTitleChanged(title, ScreenLoggerTitleView.LeftButtonType.NONE)
        }
    }

    private fun updateTitleViewForLogDetails(screenLog: ScreenLog) {
        onTitleChangeListener?.onTitleChanged(screenLog.getReadableLogTitle(), ScreenLoggerTitleView.LeftButtonType.BACK)
    }

    private fun clearAllLogs() {
        ScreenLogRepository.clearAllLogs()
    }

    private fun showLogDetails(screenLog: ScreenLog) {
        tvLogDetails.text = screenLog.getLogDetail()
        tvLogDetails.scrollTo(0, 0)
        tvLogDetails.visibility = View.VISIBLE
        llContainer.visibility = View.INVISIBLE
    }
}