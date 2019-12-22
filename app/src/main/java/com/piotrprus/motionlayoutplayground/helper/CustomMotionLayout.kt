package com.piotrprus.motionlayoutplayground.helper

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import androidx.constraintlayout.motion.widget.MotionLayout

class CustomMotionLayout : MotionLayout {
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

    override fun obtainVelocityTracker(): MotionTracker {
        return MyTracker.obtain()
    }

    /**
     * The only functional changes to this class are added null-checks and recursion avoidance in
     *
     * @see MyTracker.getYVelocity
     */
    private class MyTracker private constructor() : MotionTracker {
        var tracker: VelocityTracker? = null
        override fun recycle() {
            tracker?.let {
                recycle()
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
}