package com.piotrprus.motionlayoutplayground.helper.extensions

import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun View.awaitsKeyboard() = suspendCancellableCoroutine<Unit> { cont ->
    val callback = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
        override fun onProgress(
            p0: WindowInsets,
            p1: MutableList<WindowInsetsAnimation>
        ): WindowInsets {
            Log.d("IME", "keyboard animation in progress")
            return p0
        }

        override fun onEnd(animation: WindowInsetsAnimation) {
            cont.resume(Unit)
            Log.d("IME", "onEnd")
            setWindowInsetsAnimationCallback(null)
        }
    }
    cont.invokeOnCancellation { setWindowInsetsAnimationCallback(null) }
    setWindowInsetsAnimationCallback(callback)
}