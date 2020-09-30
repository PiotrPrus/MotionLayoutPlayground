package com.piotrprus.motionlayoutplayground.fragments.scene18

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.piotrprus.motionlayoutplayground.R
import com.piotrprus.motionlayoutplayground.databinding.ItemScene18SimpleListBinding

private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem

}

class Scene18Adapter : ListAdapter<Int, Scene18ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Scene18ListViewHolder {
        val binding: ItemScene18SimpleListBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_scene18_simple_list, parent, false
            )
        return Scene18ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Scene18ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}