package com.piotrprus.motionlayoutplayground.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.piotrprus.motionlayoutplayground.R
import com.piotrprus.motionlayoutplayground.data.model.AnimationState
import com.piotrprus.motionlayoutplayground.data.model.DummyContent
import com.piotrprus.motionlayoutplayground.helper.InterceptScrollingRecyclerView
import com.piotrprus.motionlayoutplayground.helper.MySceneGridItemRecyclerViewAdapter

/**
 * A simple [Fragment] subclass.
 */
class Scene13Fragment : Fragment() {

    private lateinit var recyclerView: InterceptScrollingRecyclerView
    private val animationStateLiveData = MutableLiveData<Pair<AnimationState, String>>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        recyclerView = inflater.inflate(
            R.layout.fragment_scene13,
            container,
            false
        ) as InterceptScrollingRecyclerView

        with(recyclerView) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter =
                MySceneGridItemRecyclerViewAdapter(
                    DummyContent.ITEMS
                ) { state, tag ->
                    Log.d("Scene13Fragment", "Animation state: $state")
                    animationStateLiveData.value = state to tag
                }
        }
        return recyclerView
    }

    private fun initObservers() {
        animationStateLiveData.observe(viewLifecycleOwner, Observer { pair ->
            when (pair.first) {
                AnimationState.STARTED, AnimationState.IN_PROGRESS -> {
                    recyclerView.swipeFrozen = true
                }
                else -> {
                    recyclerView.swipeFrozen = false
                }
            }
            recyclerView.findViewWithTag<View>(pair.second).elevation =
                when (pair.first) {
                    AnimationState.STARTED, AnimationState.IN_PROGRESS -> 1f
                    AnimationState.COMPLETED -> 0f
                }
        })
    }
}
