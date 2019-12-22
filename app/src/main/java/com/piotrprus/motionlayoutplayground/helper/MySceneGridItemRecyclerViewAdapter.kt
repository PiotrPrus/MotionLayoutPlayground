package com.piotrprus.motionlayoutplayground.helper


import android.os.Handler
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

class MySceneGridItemRecyclerViewAdapter(
    private val mValues: MutableList<DummyItem>,
    private val animationListener: (state: AnimationState) -> Unit
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

    private val removeItemListener: (Int) -> Unit = {
        Handler().postDelayed({
            mValues.removeAt(it)
            notifyDataSetChanged()
        }, 100)
    }

    private val onItemClicked: (String) -> Unit = {
        Log.d("Adapter", "ItemClicked: $it")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.id
        holder.mContentView.text = item.content
        holder.position = position
        holder.onItemClickedInVH = onItemClicked
        holder.bind()
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

        fun bind() {
            (mView as CustomMotionLayout).apply {
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
                            p1 == R.id.rest && p3 > 0f -> {
                                animationListener(AnimationState.STARTED)
                            }
                        }
                    }

                    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                        animationListener(AnimationState.COMPLETED)
                        if (p1 == R.id.goneRight || p1 == R.id.goneLeft) {
                            Log.d("Adapter", "Remove item at position: $position")
                            removeItemListener(position)
                        }
                    }

                    override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {}
                })
            }
        }
    }
}
