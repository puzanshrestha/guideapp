package com.project.guideapp.ui.shopping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentShoppingBinding


class ShoppingFragment : Fragment() {

    lateinit var binding: FragmentShoppingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingBinding.inflate(inflater)
        binding.toolbar.tvTitle.text = "Shopping"
        binding.toolbar.ivBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
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

}