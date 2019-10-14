package com.piotrprus.motionlayoutplayground.fragments.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.piotrprus.motionlayoutplayground.R
import com.piotrprus.motionlayoutplayground.data.model.StartSceneItem
import com.piotrprus.motionlayoutplayground.databinding.ItemStartSceneBinding

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StartSceneItem>() {
    override fun areItemsTheSame(oldItem: StartSceneItem, newItem: StartSceneItem): Boolean =
        oldItem.title == newItem.title


    override fun areContentsTheSame(oldItem: StartSceneItem, newItem: StartSceneItem): Boolean =
        oldItem == newItem

}

class StartListAdapter(private val onClickListener: (Int) -> Unit) :
    ListAdapter<StartSceneItem, StartListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartListViewHolder {
        val binding: ItemStartSceneBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_start_scene, parent, false
            )
        return StartListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StartListViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, onClickListener)
    }
}