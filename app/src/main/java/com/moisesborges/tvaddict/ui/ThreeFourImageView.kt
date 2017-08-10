package com.moisesborges.tvaddict.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by moises.anjos on 09/08/2017.
 */
class ThreeFourImageView(context: Context, attrs: AttributeSet): ImageView(context, attrs) {

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val threeFourHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthSpec) * 4 / 3,
                MeasureSpec.EXACTLY)
        super.onMeasure(widthSpec, threeFourHeight)
    }
}
