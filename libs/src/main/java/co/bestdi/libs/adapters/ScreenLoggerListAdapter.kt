package co.bestdi.libs.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.bestdi.libs.LogType
import co.bestdi.libs.OrderedList
import co.bestdi.libs.ScreenLog
import co.bestdi.libs.ScreenLoggerConstants
import co.bestdi.libs.filterables.LogFilterable
import co.bestdi.libs.filterables.LogTypeFilterable
import co.bestdi.libs.views.ScreenLogItemView

internal class ScreenLoggerListAdapter(
        private val isListInAscendingOrder: Boolean,
        logType: LogType
) : RecyclerView.Adapter<ScreenLoggerListAdapter.ScreenLogViewHolder>() {
    private val screenLogs: OrderedList<ScreenLog> = OrderedList(isListInAscendingOrder)
    var onLogCellClickListener: OnLogCellClickListener? = null
    private var logFilters: MutableList<LogFilterable> = arrayListOf()

    init {
        logFilters.add(LogTypeFilterable(logType))
    }

    override fun onBindViewHolder(holder: ScreenLogViewHolder, position: Int) {
        val screenLogPosition = if (isListInAscendingOrder) (screenLogs.size - 1 - position) else position
        holder.setData(screenLogs[screenLogPosition])
    }

    override fun getItemCount(): Int = screenLogs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenLogViewHolder {
        return parent.let {
            ScreenLogViewHolder(ScreenLogItemView(it.context))
        }
    }

    fun onObserverAdded(initialScreenLogs: Collection<ScreenLog>) {
        onScreenLogsAdded(initialScreenLogs)
    }

    fun onScreenLogAdded(screenLog: ScreenLog) {
        val isLogAcceptable = !logFilters.any { it.isFilterLog(screenLog) }
        if (isLogAcceptable) {
            screenLogs.add(screenLog)
            if (ScreenLoggerConstants.isListInAscendingOrder) {
                notifyItemInserted(screenLogs.size - 1)
            } else {
                notifyItemInserted(0)
            }
        }
    }

    fun onScreenLogsAdded(screenLogs: Collection<ScreenLog>) {
        val filteredScreenLogs = screenLogs.filter { screenLog ->
            !logFilters.any { it.isFilterLog(screenLog) }
        }
        if (filteredScreenLogs.isNotEmpty()) {
            val positionStart = this.screenLogs.size
            this.screenLogs.addAll(filteredScreenLogs)
            if (ScreenLoggerConstants.isListInAscendingOrder) {
                notifyItemRangeInserted(positionStart, filteredScreenLogs.size)
            } else {
                notifyItemRangeInserted(0, filteredScreenLogs.size)
            }
        }
    }

    fun onAllLogsCleared() {
        screenLogs.clear()
        notifyDataSetChanged()
    }

    inner class ScreenLogViewHolder(itemView: ScreenLogItemView) : RecyclerView.ViewHolder(itemView) {
        private val screenLogItemView = itemView

        fun setData(screenLog: ScreenLog) {
            screenLogItemView.setData(screenLog)
            screenLogItemView.setOnClickListener {
                onLogCellClickListener?.onLogCellClicked(screenLog)
            }
        }
    }

    interface OnLogCellClickListener {
        fun onLogCellClicked(screenLog: ScreenLog)
    }
}