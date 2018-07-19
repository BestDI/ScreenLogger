package co.bestdi.libs.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import co.bestdi.libs.LoggerType
import co.bestdi.libs.views.LogListContainer

internal class LoggerViewPagerAdapter(val context: Context) : PagerAdapter() {
    companion object {
        private const val PAGE_COUNT = 3

        fun getPageTitle(context: Context, position: Int): CharSequence {
            return context.getString(getPageLogType(position).getTitleRes())
        }

        fun getPageLogType(position: Int): LoggerType {
            return LoggerType.valuesOf(position)
                    ?: error("cannot find LoggerType for position $position")
        }
    }

    init {
        setupView()
    }

    override fun isViewFromObject(view: View, anyObject: Any): Boolean = view == anyObject

    override fun getCount(): Int = PAGE_COUNT

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return logListContainers[position].apply {
            container.addView(this)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, anyObject: Any) {
        val anyView = anyObject as? View
        anyView?.let {
            container.removeView(it)
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return getPageTitle(context, position)
    }

    private lateinit var logListContainers: MutableList<LogListContainer>
    var onCellClickListener: LogListContainer.OnCellClickListener? = null
        set(value) {
            if (field != value) {
                field = value
                setupOnCellClickListenerForScreenLogListContainers()
            }
        }

    private fun setupView() {
        logListContainers = arrayListOf()
        with(logListContainers) {
            (0 until PAGE_COUNT).forEach {
                add(LogListContainer(context, loggerType = getPageLogType(it)))
            }
        }
    }

    private fun setupOnCellClickListenerForScreenLogListContainers() {
        logListContainers.forEach { it.onCellClickListener = onCellClickListener }
    }
}