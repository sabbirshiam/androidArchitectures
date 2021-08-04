package com.ssh.androidarchitectures.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ssh.androidarchitectures.R
import com.ssh.androidarchitectures.model.Country
import kotlinx.android.synthetic.main.row_layout.view.*


class CountryItemView : LinearLayout {
    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.row_layout, this, true)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun bindData(data: Country) {
        listText.text = data.name
    }
}