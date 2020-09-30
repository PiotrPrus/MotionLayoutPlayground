package com.piotrprus.motionlayoutplayground.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.fragment.app.Fragment
import com.piotrprus.motionlayoutplayground.R
import kotlinx.android.synthetic.main.fragment_scene16.*

class Scene16Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scene16, container, false)
    }

    override fun onStart() {
        super.onStart()
        scene16testButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val isKeyboardVisible =
                    requireView().rootWindowInsets.isVisible(WindowInsets.Type.ime())
                val controller = requireView().windowInsetsController
                if (isKeyboardVisible) {
                    controller?.hide(WindowInsets.Type.ime())
                } else {
                    controller?.show(WindowInsets.Type.ime())
                }
            }
        }
    }
}