package com.project.guideapp.ui.streetart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.guideapp.R
import com.project.guideapp.databinding.ItemFeaturedAttractionsBinding
import com.project.guideapp.databinding.ItemStreetArtListBinding
import com.project.guideapp.network.dto.ExhibitsDTO
import com.squareup.picasso.Picasso

class StreetArtListAdapter(
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<StreetArtListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStreetArtListBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 18
    }

    class ViewHolder(private val binding: ItemStreetArtListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exhibits: ExhibitsDTO) {

        }
    }

    class OnClickListener(val clickListener: () -> Unit) {
        fun onClick() = clickListener()
    }

}