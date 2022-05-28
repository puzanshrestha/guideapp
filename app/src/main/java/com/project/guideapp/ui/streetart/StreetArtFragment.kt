package com.project.guideapp.ui.streetart

import StreetArtListAdapter
import StreetArtSearchResultsAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentStreetArtBinding


class StreetArtFragment : Fragment() {

    private lateinit var binding: FragmentStreetArtBinding
    private var searchActive = false
    private val viewModel: StreetArtViewModel by lazy {
        ViewModelProvider(this).get(StreetArtViewModel::class.java)
    }

    private lateinit var handler: Handler
    private val imageViewUpdaterRunnable = Runnable {
        updateImageView()
    }

    override fun onPause() {
        handler.removeCallbacks(imageViewUpdaterRunnable)
        super.onPause()
    }

    override fun onDestroy() {
        handler.removeCallbacks(imageViewUpdaterRunnable)
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreetArtBinding.inflate(inflater)
        binding.toolbar.tvTitle.text = "Street art museum"
        binding.toolbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        handler = Handler(Looper.getMainLooper())
        updateImageView()
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val html = HtmlCompat.fromHtml(
            "Historically, the 'West-Kruiskade' was a notorious thoroughfare due to drug trafficking, dealers, and prostitutes. Although drug trafficking and its excesses have vanished, these streets remain interesting! We began with the purpose of attracting more street art to the street in 2017, and the street has gradually transformed into the Rotterdam Street Art Museum since then. The setting, paired with the history of the area, made this project a perfect complement.",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.flowTextViewBackground.text = html
        binding.flowTextViewBackground.setTypeface(
            ResourcesCompat.getFont(
                requireContext(),
                R.font.raleway_regular
            )
        )
        binding.flowTextViewBackground.setTextSize(resources.getDimension(R.dimen.text_size))

        binding.rvSearchResults.layoutManager = LinearLayoutManager(context)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvStreetArtList.layoutManager = staggeredGridLayoutManager

        binding.ivStreetArt.setImageResource(R.drawable.img_street)

        viewModel.filteredExhibits.observe(viewLifecycleOwner) {
            binding.rvStreetArtList.adapter =
                StreetArtListAdapter(it, StreetArtListAdapter.OnClickListener {
                    var bundle = Bundle()
                    bundle.putString("ID", it.artistId)
                    view.findNavController()
                        .navigate(R.id.action_menu_street_art_to_exhibitDetailFragment, bundle)
                })
        }
        viewModel.searchResultsExhibits.observe(viewLifecycleOwner) {
            binding.llSearch.visibility = View.VISIBLE
            binding.ivSearchBack.setImageResource(R.drawable.ic_back)
            binding.tvSearchResultCount.text = getString(R.string.search_result_count, it.size)
            binding.rvSearchResults.adapter =
                StreetArtSearchResultsAdapter(it, StreetArtSearchResultsAdapter.OnClickListener {
                    var bundle = Bundle()
                    bundle.putString("ID", it.artistId)
                    view.findNavController()
                        .navigate(R.id.action_menu_street_art_to_exhibitDetailFragment, bundle)
                })

            binding.etSearchText.requestFocus()
        }

        binding.etSearchText.onFocusChangeListener =
            View.OnFocusChangeListener { p0, focus ->
                searchActive = focus
                if(!focus){
                    binding.etSearchText.hideKeyboard()
                    binding.ivSearchBack.requestFocus()
                    binding.etSearchText.clearFocus()
                    binding.llSearch.visibility = View.GONE
                    binding.ivSearchBack.setImageResource(R.drawable.ic_search)
                    binding.etSearchText.setText("")
                }
            }


        binding.ivSearchBack.setOnClickListener {
            binding.etSearchText.hideKeyboard()
            binding.ivSearchBack.requestFocus()
            binding.etSearchText.clearFocus()
            binding.llSearch.visibility = View.GONE
            binding.ivSearchBack.setImageResource(R.drawable.ic_search)
            binding.etSearchText.setText("")
        }

        binding.llFilterArt.setOnClickListener {
            val popup = PopupMenu(context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.street_art_filter_menu, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {
                viewModel.filterArtWorks(it.title.toString())
                true
            }
        }

        binding.etSearchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (searchActive)
                    viewModel.searchArt(binding.etSearchText.text.toString())
            }

        })

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

                    when (item.itemId) {
                        R.id.menu_audio -> {
                            findNavController().navigate(R.id.fragmentAudioTour)
                        }

                        R.id.menu_contact_us -> {
                            findNavController().navigate(R.id.fragmentContactUs)
                        }
                    }
                    return true
                }

                override fun onMenuModeChange(menu: MenuBuilder) {

                }

            })

        }

    }

    var imageCounter = 1
    private fun updateImageView() {
        var animFade = AnimationUtils.loadAnimation(context, R.anim.fade);
        animFade.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                if (!searchActive) {
                    when (imageCounter) {
                        0 -> {
                            imageCounter++
                            binding.ivStreetArt.setImageResource(R.drawable.img_street)
                        }
                        1 -> {
                            imageCounter++
                                binding.ivStreetArt.setImageResource(R.drawable.img_street_art1)
                        }
                        2 -> {
                            imageCounter = 0
                            binding.ivStreetArt.setImageResource(R.drawable.img_street_art2)
                        }

                    }
                }
            }
        })


        binding.ivStreetArt.startAnimation(animFade)

        handler.postDelayed(imageViewUpdaterRunnable, 30000)


    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}