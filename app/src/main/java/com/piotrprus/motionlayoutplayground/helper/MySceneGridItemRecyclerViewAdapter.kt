package com.piotrprus.motionlayoutplayground.helper


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import com.piotrprus.motionlayoutplayground.R
import com.piotrprus.motionlayoutplayground.data.model.AnimationState
import com.piotrprus.motionlayoutplayground.data.model.DummyContent.DummyItem
import kotlinx.android.synthetic.main.item_scene_13_grid.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.resume

class MySceneGridItemRecyclerViewAdapter(
    private val mValues: MutableList<DummyItem>,
    private val animationListener: (state: AnimationState, tag: String) -> Unit
) : RecyclerView.Adapter<MySceneGridItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scene_13_grid, parent, false)
        return ViewHolder(view)
    }

    private val onItemClicked: (String) -> Unit = {
        Log.d("Adapter", "ItemClicked: $it")
    }

    private val awaitListChanged: suspend () -> Unit = {
        Log.d("AAA", "await List started")
        suspendCancellableCoroutine<Int> { cont ->
            Log.d("AAA", "suspending coroutine started")
            val observer = object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    Log.d("AAA", "onItemRangeRemoved")
                    unregisterAdapterDataObserver(this)
                    cont.resume(positionStart)
                }
            }
            cont.invokeOnCancellation { unregisterAdapterDataObserver(observer) }
            registerAdapterDataObserver(observer)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.itemView.tag =
            item.id //set Tag for each binded item to identifies it in fragent/viewModel
        holder.mIdView.text = item.id
        holder.mContentView.text = item.content
        holder.position = position
        holder.onItemClickedInVH = onItemClicked
        val animationStateListener: (AnimationState) -> Unit = {
            animationListener(it, item.id)
        }
        val testRemoveListener: suspend (Int) -> Unit = { testPos ->
            mValues.removeAt(testPos)
            notifyItemRemoved(testPos)
        }
        holder.bind(animationStateListener, awaitListChanged, testRemoveListener)
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) :
        RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        internal var position = 0
        var onItemClickedInVH: ((String) -> Unit)? = null

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }

        fun bind(
            animationListener: (state: AnimationState) -> Unit,
            awaitListChanged: suspend () -> Unit,
            removeListener: suspend (Int) -> Unit
        ) {
            (mView as MotionLayout).apply {
                setTransitionListener(object : MotionLayout.TransitionListener {
                    override fun onTransitionTrigger(
                        p0: MotionLayout?,
                        p1: Int,
                        p2: Boolean,
                        p3: Float
                    ) {
                    }

                    override fun onTransitionChange(
                        p0: MotionLayout?,
                        p1: Int,
                        p2: Int,
                        p3: Float
                    ) {
                        when {
                            p1 == R.id.rest && p3 in 0.01f..0.1f -> {
                                Log.d("AAA", "State STARTED from transitionListener")
                                animationListener(AnimationState.STARTED)
                            }
                        }
                    }

                    override fun onTransitionCompleted(motionLayout: MotionLayout?, endScene: Int) {
                        animationListener(AnimationState.COMPLETED)
                        if (endScene == R.id.goneRight || endScene == R.id.goneLeft) {
                            GlobalScope.launch(Dispatchers.Main) {
                                removeListener(position)
                                awaitListChanged()
                                resetTransition(motionLayout)
                            }
                        }
                    }

                    override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {}
                })
            }
        }

        private fun resetTransition(motionLayout: MotionLayout?) {
            motionLayout?.let {
                Log.d("AAA", "transition reset")
                it.progress = 0f
                it.setTransition(R.id.rest, R.id.like)
            }
        }
    }
}
