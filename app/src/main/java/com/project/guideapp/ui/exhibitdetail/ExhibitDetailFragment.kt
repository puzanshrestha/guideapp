package com.project.guideapp.ui.exhibitdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentExhibitDetailBinding
import com.project.guideapp.databinding.FragmentHomeBinding
import com.project.guideapp.ui.home.HomeViewModel
import com.squareup.picasso.Picasso

class ExhibitDetailFragment : Fragment() {

    private lateinit var binding: FragmentExhibitDetailBinding

    private val viewModel: ExhibitDetailViewModel by lazy {
        ViewModelProvider(this).get(ExhibitDetailViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExhibitDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.exhibitDetail.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(it.images[0].imageUri)
                .placeholder(R.drawable.ic_option_menu)
                .into(binding.ivArtImageBg)
            binding.tvArtTitle.text = it.name
            binding.tvArtLocation.text = it.location
            binding.tvStory.text = it.story
        }

        val id = arguments?.getString("ID", "")
        id?.let { viewModel.getExhibitDetail(it) }
    }

}