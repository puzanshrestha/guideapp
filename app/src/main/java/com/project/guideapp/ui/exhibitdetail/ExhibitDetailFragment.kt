package com.project.guideapp.ui.exhibitdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentExhibitDetailBinding
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
        binding.viewModel = viewModel

        binding.llBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.tvBackToTop.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, 0)
        }
        binding.ivAudioShowHide.setOnClickListener {
            binding.audioPlayerView.visibility =
                if (binding.audioPlayerView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        binding.llLocation.setOnClickListener {
            findNavController().navigate(R.id.action_exhibitDetailFragment_to_mapFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.exhibitDetail.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(it.images[0].imageUri)
                .placeholder(R.drawable.ic_option_menu)
                .into(binding.ivArtImageBg)
            binding.tvArtTitle.text = it.name.split(",")[0]
            binding.tvArtistName.text = it.name.split(",")[1]
            binding.tvStory.text = it.story
            if (it.audios.isNotEmpty()) {
                binding.audioPlayerView.initWithURL(it.audios[0].audioUri)
            } else {
                binding.audioPlayerView.visibility = View.GONE
            }
        }

        val id = arguments?.getString("ID", "")
        id?.let { viewModel.getExhibitDetail(it) }
    }

}