package com.example.googlemap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.googlemap.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this@MapFragment).get(MapViewModel::class.java)

        val binding = FragmentMapBinding.inflate(inflater, container, false).apply {
            viewModel = this@MapFragment.viewModel
            lifecycleOwner = this@MapFragment
            mapView = map

            mapView.onCreate(savedInstanceState)

            mapView.getMapAsync { map ->
                this@MapFragment.map = map

                viewModel?.onMapReady(map)

                // Updates the location and zoom of the MapView
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(43.1, -87.9), 10f)
                map?.animateCamera(cameraUpdate)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()

        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()

        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()

        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()

        mapView.onLowMemory()
    }

    companion object {
        @JvmStatic
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

}
