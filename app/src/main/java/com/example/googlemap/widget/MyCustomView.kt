package com.example.googlemap.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.googlemap.R
import kotlinx.android.synthetic.main.view_my_custom.view.*

class MyCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    init {
        // Inflate
        LayoutInflater.from(context).inflate(R.layout.view_my_custom, this, true)
    }

    fun setTopTextContent(content: String) {
        top_label.text = content
    }

    fun setBottomTextContent(content: String) {
        bottom_label.text = content
    }

    fun setTopTextColor(color: Int) {
        top_label.setTextColor(color)
    }

    fun setBottomTextColor(color: Int) {
        bottom_label.setTextColor(color)
    }

}