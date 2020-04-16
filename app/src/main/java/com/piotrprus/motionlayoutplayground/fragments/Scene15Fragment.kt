package com.piotrprus.motionlayoutplayground.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.piotrprus.motionlayoutplayground.R
import kotlinx.android.synthetic.main.fragment_scene15.*

/**
 * A simple [Fragment] subclass.
 */
class Scene15Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scene15, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var touchPositionPairDown: Pair<Float?, Float?> = null to null
//        scene15Square.setOnTouchListener { v, event ->
//            Log.d("AAA", "square touched!!")
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> touchPositionPairDown = event.x to event.y
//                MotionEvent.ACTION_UP -> checkViewForClick(
//                    touchPositionPairDown,
//                    event.x to event.y
//                )
//            }
//            true
//        }
    }

    private fun performClick() {
        Log.d("AAA", "performClick()")
        Toast.makeText(requireContext(), "Click!!!", Toast.LENGTH_SHORT).show()
    }

    private fun checkPositionsForClick(
        pairDown: Pair<Float, Float>,
        pairUp: Pair<Float, Float>
    ): Boolean {
        val horizontalDeltaOfTouch = pairDown.first.minus(pairUp.first)
        val verticalDeltaOfTouch = pairDown.second.minus(pairUp.second)
        Log.d("AAA", "horizontalDelta: $horizontalDeltaOfTouch, verticalDelta: $verticalDeltaOfTouch")
        return horizontalDeltaOfTouch < 10f && verticalDeltaOfTouch < 10f
//        pairDown.first.minus(pairUp.first) < 4f &&
//                pairDown.second.minus(pairUp.second) < 4f
    }
}
