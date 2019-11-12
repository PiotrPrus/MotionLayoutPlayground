package com.piotrprus.motionlayoutplayground.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.piotrprus.motionlayoutplayground.R
import kotlinx.android.synthetic.main.fragment_scene_10.*

/**
 * A simple [Fragment] subclass.
 */
class Scene10Fragment : Fragment(), MotionLayout.TransitionListener {
    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) {
        if (progress > 0.9f) {
            findNavController().navigate(R.id.action_scene10Fragment_to_scene10Fragment2)
        }
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scene_10, container, false)
    }

    override fun onStart() {
        super.onStart()
        motionLayoutFragment10.setTransitionListener(this)
    }


}
