package co.bestdi.libs.views

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import co.bestdi.libs.adapters.LoggerViewPagerAdapter

internal class LoggerViewPager @JvmOverloads internal constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    var onTitleChangeListener: OnTitleChangeListener? = null
        set(value) {
            if (field != value) {
                field = value
                // since onPageSelected is not called when init
                notifyOnTitleChanged(currentItem)
            }
        }

    init {
        setupView()
    }

    private fun setupView() {
        addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                notifyOnTitleChanged(position)
            }
        })
    }

    private fun notifyOnTitleChanged(position: Int) {
        onTitleChangeListener?.onTitleChanged(
                context.getString(LoggerViewPagerAdapter.getPageLogType(position).getTitleRes()))
    }

    interface OnTitleChangeListener {
        fun onTitleChanged(title: String?, leftButtonType: LoggerTitleView.LeftButtonType = LoggerTitleView.LeftButtonType.NONE)
    }
}