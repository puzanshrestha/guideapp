package com.project.guideapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentHomeBinding
import com.project.guideapp.network.dto.ExhibitsDTO
import com.project.guideapp.ui.MainActivity
import com.squareup.picasso.Picasso


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.toolbar.tvTitle.text = "Home"
        binding.toolbar.ivBack.visibility = View.GONE

        binding.rvFeaturedAttractions.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return binding.root
    }


    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.ivOptionMenu.setOnClickListener {
            val menuBuilder = MenuBuilder(context)
            val inflater = MenuInflater(context)
            inflater.inflate(R.menu.option_menu, menuBuilder)
            val optionsMenu =
                MenuPopupHelper(requireContext(), menuBuilder, binding.toolbar.ivOptionMenu)
            optionsMenu.setForceShowIcon(true)
            optionsMenu.show()
            menuBuilder.setCallback(object : MenuBuilder.Callback {
                override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {

                    when (item.itemId){
                        R.id.menu_audio->{
                            findNavController().navigate(R.id.action_menu_home_to_fragmentAudioTour)
                        }

                        R.id.menu_contact_us->{
                            findNavController().navigate(R.id.action_menu_home_to_fragmentContactUs)
                        }
                    }
                    return true
                }

                override fun onMenuModeChange(menu: MenuBuilder) {

                }

            })

        }

        viewModel.exhibits.observe(viewLifecycleOwner) {
            var featuredAttractionsAdapter = FeaturedAttractionsAdapter(it,
                FeaturedAttractionsAdapter.OnClickListener {
                    var bundle = Bundle()
                    bundle.putString("ID", it.artistId)
                    view.findNavController()
                        .navigate(R.id.action_menu_home_to_exhibitDetailFragment, bundle)
                })
            binding.rvFeaturedAttractions.adapter = featuredAttractionsAdapter

            updateTopViews(it)
        }
    }

    private fun updateTopViews(exhibitList: List<ExhibitsDTO>?) {
        val firstItem = exhibitList?.random()
        val secondItem = exhibitList?.random()
        val thirdItem = exhibitList?.random()

        /* updateTopItemViews(
             firstItem,
             binding.ivTopLeft,
             binding.tvTopLeftTitle,
             binding.tvTopLeftDescription
         )
         updateTopItemViews(
             secondItem,
             binding.ivTopRight,
             binding.tvTopRightTopTitle,
             binding.tvTopRightTopDescription
         )
         updateTopItemViews(
             thirdItem,
             binding.ivTopLeft,
             binding.tvTopRightBottomTitle,
             binding.tvTopRightBottomDescription
         )*/
        binding.ivTopLeft.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menu_home_to_menu_street_art)
        }
        binding.ivTopRightTop.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menu_home_to_restaurantsFragment)
        }
        binding.ivTopRightBottom.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_menu_home_to_shoppingFragment)
        }
    }

    private fun updateTopItemViews(
        exhibitsDTO: ExhibitsDTO?,
        imageView: ImageView,
        textViewTitle: TextView,
        textViewDescription: TextView
    ) {
        exhibitsDTO?.let {
            if (it.images.isNotEmpty())
                Picasso.get()
                    .load(it.images[0].imageUri)
                    .placeholder(R.drawable.ic_option_menu)
                    .into(imageView)

            textViewTitle.text = it.name
            textViewDescription.text = it.story
        }
    }
}