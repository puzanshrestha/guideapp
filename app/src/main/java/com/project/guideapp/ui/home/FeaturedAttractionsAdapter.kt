package com.project.guideapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.guideapp.databinding.ItemFeaturedAttractionsBinding

class FeaturedAttractionsAdapter : RecyclerView.Adapter<FeaturedAttractionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFeaturedAttractionsBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }

    class ViewHolder(binding: ItemFeaturedAttractionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}