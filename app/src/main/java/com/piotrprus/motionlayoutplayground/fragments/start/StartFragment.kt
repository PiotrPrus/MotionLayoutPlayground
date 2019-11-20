package com.piotrprus.motionlayoutplayground.fragments.start


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.piotrprus.motionlayoutplayground.R
import com.piotrprus.motionlayoutplayground.data.model.StartSceneItem
import com.piotrprus.motionlayoutplayground.databinding.FragmentStartBinding

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : Fragment() {

    lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = StartListAdapter(navigateToScene)
        binding.startFragmentRV.adapter = adapter
        adapter.submitList(startSceneList)
    }

    private val navigateToScene: (Int) -> Unit = { destination ->
        findNavController().navigate(destination)
    }

    private val startSceneList: List<StartSceneItem>
        get() {
            return listOf(
                StartSceneItem(
                    resources.getString(R.string.scene_one_title),
                    R.id.scene01Fragment,
                    resources.getString(R.string.scene_one_info)
                ),
                StartSceneItem(
                    getString(R.string.scene_two_title), R.id.scene02Fragment, getString(
                        R.string.scene_two_info
                    )
                ),
                StartSceneItem(
                    getString(R.string.scene_three_title),
                    R.id.scene03Fragment,
                    getString(R.string.scene_three_info)
                ),
                StartSceneItem(
                    getString(R.string.scene_four_title), R.id.scene04Fragment, getString(
                        R.string.scene_four_info
                    )
                ),
                StartSceneItem(
                    getString(R.string.scene_five_title), R.id.scene05Fragment, getString(
                        R.string.scene_five_info
                    )
                ),
                StartSceneItem(
                    getString(R.string.scene_six_title),
                    R.id.scene06Fragment,
                    getString(R.string.scene_six_info)
                ),
                StartSceneItem(
                    getString(R.string.sceneSevenTitle), R.id.scene07Fragment, getString(
                        R.string.sceneSevenInfo
                    )
                ),
                StartSceneItem(
                    getString(R.string.sceneEightTitle), R.id.scene08Fragment, getString(
                        R.string.sceneEightInfo
                    )
                ),
                StartSceneItem(
                    getString(R.string.sceneNineTitle), R.id.scene09Fragment, getString(
                        R.string.sceneNineInfo
                    )
                ),
                StartSceneItem(
                    getString(R.string.scene10Title), R.id.scene10Fragment, getString(
                        R.string.scene10Info
                    )
                ),
                StartSceneItem("scene 12(infinite swipe)", R.id.scene12Fragment, "test test"),
                StartSceneItem(
                    "scene13(RV with ML)",
                    R.id.scene13Fragment,
                    "test ML in recyclerView cell"
                )
            )
        }

}
