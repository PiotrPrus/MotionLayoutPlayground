package com.piotrprus.motionlayoutplayground.fragments

import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.piotrprus.motionlayoutplayground.R
import com.piotrprus.motionlayoutplayground.helper.extensions.awaitsKeyboard
import kotlinx.android.synthetic.main.fragment_scene19.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Scene19Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scene19, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupInsetListener()

    }

    override fun onStart() {
        super.onStart()
        boxScene19.setOnClickListener {
                toggleKeyboard()
            }
        }

    private fun toggleKeyboard() {
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

    private fun setupInsetListener() {
        val cb = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
            var imeHeight = 1

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onStart(
                animation: WindowInsetsAnimation,
                bounds: WindowInsetsAnimation.Bounds
            ): WindowInsetsAnimation.Bounds {
                imeHeight = requireView().rootWindowInsets.getInsets(WindowInsets.Type.ime()).bottom
                return super.onStart(animation, bounds)
            }

            override fun onEnd(animation: WindowInsetsAnimation) {
                super.onEnd(animation)
                (requireView() as MotionLayout).apply {
                    setTransition(R.id.scene19start, R.id.scene19end)
                    transitionToEnd()
                }
            }

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onProgress(
                insets: WindowInsets,
                animations: MutableList<WindowInsetsAnimation>
            ): WindowInsets {
                val typesInset = insets.getInsets(WindowInsets.Type.ime())
                val otherInset = insets.getInsets(WindowInsets.Type.systemBars())

                val diff = Insets.subtract(typesInset, otherInset).let {
                    Insets.max(it, Insets.NONE)
                }

                val imeProgress = diff.bottom.div(imeHeight)
                (requireView() as MotionLayout).apply {
                    setTransition(R.id.scene19start, R.id.scene19end)
                    setProgress(imeProgress.toFloat(), imeProgress.toFloat())
                }

                return insets
            }
        }
        requireView().rootView.setWindowInsetsAnimationCallback(cb)
    }
}