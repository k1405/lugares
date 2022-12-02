package com.lugares.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lugares.databinding.FragmentGalleryBinding
import com.lugares.viewmodel.GalleryViewModel

class GalleryFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentGalleryBinding? = null

    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap
    private var mapReady = false;

    private lateinit var lugarViewModel: LugarViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.map.onCreate(savedInstanceState)
        binding.map.onResume()
        binding.map.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        map.let{
            googleMap= it
            mapReady=true


            lugarViewModel.getLugares.observe(viewLifecycleOwner){lugares -> updatedmap(lugares)}

        }
    }

    private fun updatedmap(lugares: List<Lugar>) {

        if (mapReady){

            lugares.forEach{ lugar->
                if(lugar.latitud?.isFinite()==true && lugar.longitud?.isFinite()==true){
                    val marca = LatLng(lugar.latitud,lugar.longitud)
                    googleMap.addMarker(MarkerOptions().position(marca).title(lugar.nombre))

                }
            }

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this)[(LugarViewModel::class.java]

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}