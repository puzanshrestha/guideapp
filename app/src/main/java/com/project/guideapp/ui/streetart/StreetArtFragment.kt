package com.project.guideapp.ui.streetart

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentStreetArtBinding


class StreetArtFragment : Fragment() {

    private lateinit var binding: FragmentStreetArtBinding

    private val viewModel: StreetArtViewModel by lazy {
        ViewModelProvider(this).get(StreetArtViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreetArtBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val html = HtmlCompat.fromHtml(
            "In 2017, we started to attract more street art to the ‘West-Kruiskade’ Since then the street slowly started to transform into the Rotterdam Street Art Museum. All artworks are located in the main and side ",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.flowTextViewBackground.text = html
        binding.flowTextViewBackground.setTypeface(
            ResourcesCompat.getFont(
                context!!,
                R.font.raleway_regular
            )
        )
        binding.flowTextViewBackground.setTextSize(resources.getDimension(R.dimen.text_size))

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvStreetArtList.layoutManager = staggeredGridLayoutManager

        binding.ivStreetArt.setImageResource(R.drawable.img_street)
        binding.rvStreetArtList.adapter =
            StreetArtListAdapter(StreetArtListAdapter.OnClickListener {

            })

    }


}