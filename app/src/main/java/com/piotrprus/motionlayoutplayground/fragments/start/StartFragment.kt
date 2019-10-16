package com.piotrprus.motionlayoutplayground.fragments.start


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
                    R.id.sceneOneFragment,
                    resources.getString(R.string.scene_one_info)
                ),
                StartSceneItem(
                    getString(R.string.scene_two_title), R.id.sceneTwoFragment, getString(
                        R.string.scene_two_info
                    )
                ),
                StartSceneItem(
                    getString(R.string.scene_three_title),
                    R.id.sceneThreeFragment,
                    getString(R.string.scene_three_info)
                ),
                StartSceneItem(
                    getString(R.string.scene_four_title), R.id.sceneFourFragment, getString(
                        R.string.scene_four_info
                    )
                ),
                StartSceneItem(
                    getString(R.string.scene_five_title), R.id.sceneFiveFragment, getString(
                        R.string.scene_five_info
                    )
                ),
                StartSceneItem(
                    getString(R.string.scene_six_title),
                    R.id.sceneSixFragment,
                    getString(R.string.scene_six_info)
                ),
                StartSceneItem(
                    getString(R.string.sceneSevenTitle), R.id.sceneSevenFragment, getString(
                        R.string.sceneSevenInfo
                    )
                ),
                StartSceneItem(
                    getString(R.string.sceneEightTitle), R.id.sceneEightFragment, getString(
                        R.string.sceneEightInfo
                    )
                ),
                StartSceneItem(
                    getString(R.string.sceneNineTitle), R.id.sceneNineFragment, getString(
                        R.string.sceneNineInfo
                    )
                )
            )
        }

}
