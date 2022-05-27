package com.project.guideapp.ui.webviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebViewClient
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.navigation.fragment.findNavController
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentAudioTourBinding

class FragmentAudioTour : Fragment() {

    lateinit var binding: FragmentAudioTourBinding
    val AUDIO_TOUR_URL ="https://izi.travel/en/2e73-de-verhalen-van-de-west-kruiskade/nl"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioTourBinding.inflate(layoutInflater)
        binding.toolbar.tvTitle.text = "Audio tour"
        binding.toolbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webview.webViewClient = WebViewClient()
        binding.webview.loadUrl(AUDIO_TOUR_URL)
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.setSupportZoom(true)


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
//                            findNavController().navigate(R.id.action_menu_home_to_fragmentAudioTour)
                        }

                        R.id.menu_contact_us->{
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


}