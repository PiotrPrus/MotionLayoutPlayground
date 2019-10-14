package com.piotrprus.motionlayoutplayground.fragments.start

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.piotrprus.motionlayoutplayground.data.model.StartSceneItem
import com.piotrprus.motionlayoutplayground.databinding.ItemStartSceneBinding

class StartListViewHolder(private val binding: ItemStartSceneBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: StartSceneItem, onClickListener: (Int) -> Unit) {
        binding.itemStartSceneButton.text = item.title
        binding.itemStartSceneButton.setOnClickListener { onClickListener(item.destination) }
        binding.itemStartInfoButton.setOnClickListener { showDialog(item) }
    }

    private fun showDialog(item: StartSceneItem) {
        AlertDialog.Builder(binding.root.context)
            .setTitle(item.title)
            .setMessage(item.info)
            .show()
    }
}