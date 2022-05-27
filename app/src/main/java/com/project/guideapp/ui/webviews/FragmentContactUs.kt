package com.project.guideapp.ui.webviews

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.guideapp.R
import com.project.guideapp.databinding.FragmentContactUsBinding


class FragmentContactUs : Fragment() {

    lateinit var binding: FragmentContactUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactUsBinding.inflate(inflater)
        binding.toolbar.tvTitle.text = "Contact us"
        binding.toolbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            binding.etEmail.setText("")
            binding.etName.setText("")
            showDialog()
        }

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
                            findNavController().navigate(R.id.fragmentAudioTour)
                        }

                        R.id.menu_contact_us->{
//                            findNavController().navigate(R.id.action_menu_home_to_fragmentContactUs)
                        }
                    }
                    return true
                }

                override fun onMenuModeChange(menu: MenuBuilder) {

                }

            })

        }


    }

    fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_contact_us)
        val text = dialog.findViewById(R.id.btn_close) as TextView
        text.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }


}