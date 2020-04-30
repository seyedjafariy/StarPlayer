package com.worldsnas.starplayer.view.custom

import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.widget.AppCompatTextView

class IransansTextView constructor(context: Context) : AppCompatTextView(context) {

    init {
        init(context)
    }

    private fun init(context: Context) {
        setFont(context)

    }

    private fun setFont(context: Context) {
        val font = Typeface.createFromAsset(context.assets, "fonts/iransans.ttf")
        typeface = font


    }
}