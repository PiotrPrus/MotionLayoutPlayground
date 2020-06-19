package com.piotrprus.motionlayoutplayground.helper

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlin.math.abs

class InterceptMotionLayout : MotionLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    private var startX: Float? = null
    private var startY: Float? = null

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val dispatch = super.dispatchTouchEvent(ev)
        ev?.let { motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = motionEvent.x
                    startY = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    val endX = motionEvent.x
                    val endY = motionEvent.y
                    if (isAClick(startX, endX, startY, endY)) {
                        doAClick()
                        return true
                    }
                }
            }
        }
        return dispatch
    }

    private fun doAClick() {
        Log.d("AAA", "It is a click!!")
    }

    private fun isAClick(startX: Float?, endX: Float, startY: Float?, endY: Float): Boolean {
        if (startX == null || startY == null) return false
        if (this.progress > 0.05f) return false
        val diffX = abs(startX.minus(endX))
        val diffY = abs(startY.minus(endY))
        return (diffX < 200 && diffY < 200)
    }
}