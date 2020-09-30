package com.piotrprus.motionlayoutplayground.fragments.scene18

import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import com.piotrprus.motionlayoutplayground.R
import kotlinx.android.synthetic.main.fragment_scene18.*

class Scene18Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scene18, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupInsetListener()
        val adapter = Scene18Adapter()
        scene18RecyclerView.adapter = adapter
        adapter.submitList(listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    }

    override fun onStart() {
        super.onStart()
        controlKeyboardButton.setOnClickListener {
            controlKeyboard()
        }
    }

    private fun controlKeyboard() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val isKeyboardVisible =
                scene18RecyclerView.rootWindowInsets.isVisible(WindowInsets.Type.ime())
            val controller = scene18RecyclerView.windowInsetsController
            if (isKeyboardVisible) {
                controller?.hide(WindowInsets.Type.ime())
            } else {
                controller?.show(WindowInsets.Type.ime())
            }
        }
    }

    private fun setupInsetListener() {
        val cb = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
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

                scene18RecyclerView.translationY = (diff.top - diff.bottom).toFloat()
                scene18editText.translationY = (diff.top - diff.bottom).toFloat()

                return insets
            }
        }
        requireView().rootView.setWindowInsetsAnimationCallback(cb)
    }
}