package com.piotrprus.motionlayoutplayground.helper

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlin.math.abs

class CustomMotionLayout : MotionLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    override fun obtainVelocityTracker(): MotionTracker {
        return MyTracker.obtain()
    }

    private var startX: Float? = null
    private var startY: Float? = null
    var clickListener: (() -> Unit)? = null

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        val dispatch = super.dispatchTouchEvent(event)
        event?.let { motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = motionEvent.x
                    startY = motionEvent.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val endX = motionEvent.x
                    val endY = motionEvent.y
                    if (isUserMovingFinger(startX, endX, startY, endY)) {
                        clearStartPosition()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    val endX = motionEvent.x
                    val endY = motionEvent.y
                    if (isAClick(startX, endX, startY, endY)) {
                        doAClick()
                        return dispatch
                    }
                }
            }
        }
        return dispatch
    }

    private fun isUserMovingFinger(startX: Float?, endX: Float, startY: Float?, endY: Float): Boolean {
        if (startX == null || startY == null) return true
        return !isTouchInClickArea(startX, endX, startY, endY)
    }

    private fun clearStartPosition() {
        startX = null
        startY = null
    }

    private fun doAClick() {
        clickListener?.invoke()
    }

    private fun isAClick(startX: Float?, endX: Float, startY: Float?, endY: Float): Boolean {
        return when {
            startX == null || startY == null -> false
            isDuringSwipe() -> false
            else -> isTouchInClickArea(startX, endX, startY, endY)
        }
    }

    private fun isTouchInClickArea(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
        val diffX = abs(startX.minus(endX))
        val diffY = abs(startY.minus(endY))
        return (diffX <= MAX_DISTANCE_DEFINING_CLICK && diffY <= MAX_DISTANCE_DEFINING_CLICK)
    }

    private fun isDuringSwipe() = this.progress > MIN_PROGRESS_DEFINING_SWIPE

    /**
     * The only functional changes to this class are added null-checks and recursion avoidance in
     *
     * @see MyTracker.getYVelocity
     */
    private class MyTracker private constructor() : MotionTracker {
        var tracker: VelocityTracker? = null
        override fun recycle() {
            tracker?.let {
                it.recycle()
                tracker = null
            }
        }

        override fun clear() {
            tracker?.clear()
        }

        override fun addMovement(event: MotionEvent) {
            tracker?.addMovement(event)
        }

        override fun computeCurrentVelocity(units: Int) {
            tracker?.computeCurrentVelocity(units)
        }

        override fun computeCurrentVelocity(
            units: Int,
            maxVelocity: Float
        ) {
            tracker?.computeCurrentVelocity(units, maxVelocity)
        }

        override fun getXVelocity(): Float {
            return tracker?.xVelocity ?: 0f
        }

        override fun getYVelocity(): Float {
            return tracker?.yVelocity ?: 0f
        }

        override fun getXVelocity(id: Int): Float {
            return tracker?.getXVelocity(id) ?: 0f
        }

        override fun getYVelocity(id: Int): Float {
            return tracker?.getYVelocity(id) ?: 0f
        }

        companion object {
            private val me =
                MyTracker()

            fun obtain(): MyTracker {
                me.tracker =
                    VelocityTracker.obtain()
                return me
            }
        }
    }

    companion object {
        const val MAX_DISTANCE_DEFINING_CLICK = 200
        const val MIN_PROGRESS_DEFINING_SWIPE = 0.05f
    }
}