package com.example.googlemap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.googlemap.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

class SomeFragment : Fragment() {

    var mapView: MapView? = null
    var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_some, container, false)

        mapView = view.findViewById(R.id.map)
        mapView?.onCreate(savedInstanceState)

        mapView?.getMapAsync {
            this.map = it

            // Updates the location and zoom of the MapView
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(43.1, -87.9), 10f)
            map?.animateCamera(cameraUpdate)
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()

        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()

        mapView?.onPause()
    }

    companion object {
        @JvmStatic
        fun newInstance(): SomeFragment {
            return SomeFragment()
        }
    }
}
