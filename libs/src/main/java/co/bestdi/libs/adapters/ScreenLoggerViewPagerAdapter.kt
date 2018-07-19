package co.bestdi.libs.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import co.bestdi.libs.LogType
import co.bestdi.libs.views.ScreenLogListContainer

internal class ScreenLoggerViewPagerAdapter(val context: Context) : PagerAdapter() {
    companion object {
        private const val PAGE_COUNT = 3

        fun getPageTitle(context: Context, position: Int): CharSequence {
            return context.getString(getPageLogType(position).getTitleRes())
        }

        fun getPageLogType(position: Int): LogType {
            return LogType.valuesOf(position)
                    ?: error("cannot find LogType for position $position")
        }
    }

    init {
        setupView()
    }

    override fun isViewFromObject(view: View, anyObject: Any): Boolean = view == anyObject

    override fun getCount(): Int = PAGE_COUNT

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return screenLogListContainers[position].apply {
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

    private lateinit var screenLogListContainers: MutableList<ScreenLogListContainer>
    var onCellClickListener: ScreenLogListContainer.OnCellClickListener? = null
        set(value) {
            if (field != value) {
                field = value
                setupOnCellClickListenerForScreenLogListContainers()
            }
        }

    private fun setupView() {
        screenLogListContainers = arrayListOf()
        with(screenLogListContainers) {
            (0 until PAGE_COUNT).forEach {
                add(ScreenLogListContainer(context, logType = getPageLogType(it)))
            }
        }
    }

    private fun setupOnCellClickListenerForScreenLogListContainers() {
        screenLogListContainers.forEach { it.onCellClickListener = onCellClickListener }
    }
}