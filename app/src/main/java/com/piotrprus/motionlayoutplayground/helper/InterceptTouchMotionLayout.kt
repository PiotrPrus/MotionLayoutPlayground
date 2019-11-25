package com.piotrprus.motionlayoutplayground.helper

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import com.piotrprus.motionlayoutplayground.R

class InterceptTouchMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private var startX: Float? = null
    private var startY: Float? = null

    var onClickEvent: (() -> Unit)? = null
    private val cardView: View by lazy { this.findViewById<CardView>(R.id.cardViewScene13) }
    var isInProgress: Boolean = false

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val isInTarget = touchEventInsideTargetView(cardView, event)
            Log.d(
                "$this",
                "click interceptTouchEvent, progress: $progress , isInTarget: $isInTarget"
            )
            return if (isInProgress || isInTarget) {
                Log.d(
                    "MotionLayout",
                    "onIntercept with SUPER, progress: $progress and isInTarget: $isInTarget"
                )
                super.onInterceptTouchEvent(event)
            } else {
                Log.d("MotionLayout", "onIntercept in ELSE")
                true
            }
        } ?: return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(
            "MotionLayout",
            "onTouchEvent: $event and isInProgress: $isInProgress and progress: $progress"
        )
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            if (touchEventInsideTargetView(cardView, ev)) {
//                isInProgress = this.progress > 0f
                Log.d(
                    "MotionLayout",
                    "Event is inside target view: ${ev.action} and progress: $progress"
                )
                when (ev.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = ev.x
                        startY = ev.y
                        Log.d("AAA", "Action down with progress: $progress")
//                        if (isInProgress) {
//                            transitionToStart()
//                            this.progress = 0.0f
//                            return true
//                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        val endX = ev.x
                        val endY = ev.y
                        if (isAClick(startX!!, endX, startY!!, endY)) {
                            if (isClickHandled()) {
                                onClickEvent?.invoke()
                                return true
                            }
                        }
                    }
                }
            }
            Log.d("MotionLayout", "Event to dispatch with SUPER: $ev")
            return super.dispatchTouchEvent(ev)
        } ?: return super.dispatchTouchEvent(ev)
    }

    private fun isClickHandled(): Boolean {
        Log.d("AAA", "isClickHandled with progress: $progress")
        return when {
            progress > 0.0f -> {
//                transitionToStart().also { progress = 0f }
                false
            }
            else -> true
        }

    }

    private fun touchEventInsideTargetView(v: View, ev: MotionEvent): Boolean {
        if (ev.x > v.left && ev.x < v.right) {
            if (ev.y > v.top && ev.y < v.bottom) {
                return true
            }
        }
        return false
    }

    private fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
        val differenceX = Math.abs(startX - endX)
        val differenceY = Math.abs(startY - endY)
        val isClick = !/* =5 */(differenceX > 200 || differenceY > 200)
        Log.d("MotionLayout", "It is a click: $isClick")
        return isClick
    }
}

@BindingAdapter("onMotionItemClick")
fun InterceptTouchMotionLayout.onMotionItemClick(onClick: () -> Unit) {
    onClickEvent = onClick
}