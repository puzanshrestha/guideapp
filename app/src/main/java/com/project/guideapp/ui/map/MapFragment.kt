package com.project.guideapp.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.project.guideapp.R


class MapFragment : Fragment() {

lateinit var ivOptionMenu:ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_map, container, false)

        var ivBack: ImageView = view.findViewById(R.id.iv_back)
        var tvTitle: TextView = view.findViewById(R.id.tv_title)
        ivOptionMenu=view.findViewById(R.id.iv_option_menu)

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        tvTitle.text = "Google maps"
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        supportMapFragment!!.getMapAsync { googleMap ->
            val latLng = LatLng(51.920080, 4.467650)

            val markerOptions = MarkerOptions()

            markerOptions.position(latLng)

            markerOptions.title("the teapot")

            googleMap.clear()

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))

            googleMap.addMarker(markerOptions)
        }
        return view
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivOptionMenu.setOnClickListener {
            val menuBuilder = MenuBuilder(context)
            val inflater = MenuInflater(context)
            inflater.inflate(R.menu.option_menu, menuBuilder)
            val optionsMenu =
                MenuPopupHelper(requireContext(), menuBuilder, ivOptionMenu)
            optionsMenu.setForceShowIcon(true)
            optionsMenu.show()
            menuBuilder.setCallback(object : MenuBuilder.Callback {
                override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {

                    when (item.itemId){
                        R.id.menu_audio->{
                            findNavController().navigate(R.id.fragmentAudioTour)
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