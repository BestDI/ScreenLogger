package co.bestdi.libs.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.bestdi.libs.R

internal class ScreenLoggerTitleView @JvmOverloads internal constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    enum class LeftButtonType {
        NONE,
        BACK,
        CLOSE, ;
    }

    private lateinit var tvTitle: TextView
    private lateinit var btnLeft: ImageView
    var onTitleViewClickListener: OnTitleViewClickListener? = null
    var titleText: String? = null
        set(value) {
            if (field != value) {
                field = value
                setTitle()
            }
        }
    var leftButtonType: LeftButtonType = LeftButtonType.NONE
        set(value) {
            if (field != value) {
                field = value
                updateLeftButton()
            }
        }

    init {
        initView()
        bindView()
        setupView()
    }

    private fun initView() {
        orientation = LinearLayout.HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.view_screen_logger_title, this)
    }

    private fun bindView() {
        tvTitle = findViewById(R.id.tvTitle)
        btnLeft = findViewById(R.id.btnLeft)
    }

    private fun setupView() {
        tvTitle.setOnClickListener { onTitleViewClickListener?.onTitleClicked() }
        btnLeft.setOnClickListener { onTitleViewClickListener?.onLeftBtnClicked() }
    }

    private fun setTitle() {
        tvTitle.text = titleText
    }

    private fun updateLeftButton() {
        when (leftButtonType) {
            LeftButtonType.NONE -> {
                btnLeft.visibility = View.INVISIBLE
            }
            LeftButtonType.BACK -> {
                btnLeft.visibility = View.VISIBLE
                btnLeft.setImageResource(R.drawable.home_icon_next)
            }
            LeftButtonType.CLOSE -> {
                btnLeft.visibility = View.VISIBLE
//                btnLeft.setImageResource(R.drawable.home_icon_close)
            }
        }
    }

    interface OnTitleViewClickListener {
        fun onLeftBtnClicked()

        fun onTitleClicked()
    }
}