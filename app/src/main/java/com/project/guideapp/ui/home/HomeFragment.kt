package com.project.guideapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.toolbar.tvTitle.text = "Home"
        binding.toolbar.ivBack.visibility = View.GONE
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

        }
    }


}