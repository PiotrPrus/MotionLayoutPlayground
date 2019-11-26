package com.piotrprus.motionlayoutplayground.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.piotrprus.motionlayoutplayground.R

/**
 * A simple [Fragment] subclass.
 */
class Scene14Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val motionLayout = inflater.inflate(R.layout.fragment_scene14, container, false)
        setupMotionLayout(motionLayout)
        return motionLayout
    }

    private fun setupMotionLayout(motionLayout: View?) {
        if (motionLayout is MotionLayout) {
            val button = motionLayout.findViewById<Button>(R.id.scene14Button)
            button.setOnClickListener {
                motionLayout.setTransition(R.id.buttonAndTextAppears, R.id.prepareToLaunch)
                motionLayout.transitionToEnd()
            }
        }
    }


}
