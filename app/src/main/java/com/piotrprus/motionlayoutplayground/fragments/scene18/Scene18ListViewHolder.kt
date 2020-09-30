package com.piotrprus.motionlayoutplayground.fragments.scene18

import androidx.recyclerview.widget.RecyclerView
import com.piotrprus.motionlayoutplayground.databinding.ItemScene18SimpleListBinding

class Scene18ListViewHolder(private val binding: ItemScene18SimpleListBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(number: Int) {
        binding.scene18NumberTV.text = number.toString()
    }
}