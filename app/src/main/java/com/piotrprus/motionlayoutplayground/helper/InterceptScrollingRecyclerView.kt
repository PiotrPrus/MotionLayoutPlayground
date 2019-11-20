package com.piotrprus.motionlayoutplayground.helper

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class InterceptScrollingRecyclerView: RecyclerView {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    var swipeFrozen = false

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (swipeFrozen) {
            return false
        }
        return super.onInterceptTouchEvent(e)
    }
}