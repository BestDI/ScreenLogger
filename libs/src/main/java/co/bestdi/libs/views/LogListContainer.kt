package co.bestdi.libs.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import co.bestdi.libs.*
import co.bestdi.libs.adapters.LoggerListAdapter

internal class LogListContainer @JvmOverloads internal constructor(
        context: Context?,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0,
        loggerType: LoggerType
) : LinearLayout(context, attributeSet, defStyleAttr) {
    private lateinit var rvLogs: RecyclerView

    var onCellClickListener: OnCellClickListener? = null

    private val listAdapter: LoggerListAdapter by lazy { LoggerListAdapter(LoggerConstants.isListInAscendingOrder, loggerType) }

    init {
        initView()
        bindView()
        setupView()
    }

    private fun initView() {
        orientation = LinearLayout.VERTICAL
        LayoutInflater.from(context).inflate(R.layout.view_screen_log_list_container, this)
    }

    private fun bindView() {
        rvLogs = findViewById(R.id.rvLogs)
    }

    private fun setupView() {
        with(listAdapter) {
            rvLogs.adapter = this
            rvLogs.layoutManager = LinearLayoutManager(context)
            this.onLogCellClickListener = object : LoggerListAdapter.OnLogCellClickListener {
                override fun onLogCellClicked(screenLog: ScreenLog) {
                    onCellClickListener?.onCellClicked(screenLog)
                }
            }
        }
        setupScreenLogObservableForAdapter()
    }

    private fun setupScreenLogObservableForAdapter() {
        LoggerRepository.addScreenLogObserver(object : LoggerRepository.ScreenLogDataObserver {
            override fun onObserverAdded(initialScreenLogs: Collection<ScreenLog>) {
                post {
                    listAdapter.onObserverAdded(initialScreenLogs)
                }
            }

            override fun onScreenLogAdded(screenLog: ScreenLog) {
                post {
                    listAdapter.onScreenLogAdded(screenLog)
                }
            }

            override fun onScreenLogsAdded(screenLogs: Collection<ScreenLog>) {
                post {
                    listAdapter.onScreenLogsAdded(screenLogs)
                }
            }

            override fun onAllLogsCleared() {
                post {
                    listAdapter.onAllLogsCleared()
                }
            }
        })
    }

    interface OnCellClickListener {
        fun onCellClicked(screenLog: ScreenLog)
    }
}