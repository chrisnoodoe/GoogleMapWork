package com.example.googlemap.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.googlemap.R
import kotlinx.android.synthetic.main.view_my_custom.view.*

class Type1View @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    init {
        // Inflate
        LayoutInflater.from(context).inflate(R.layout.view_my_custom, this, true)
    }

}