package com.example.googlemap.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.googlemap.databinding.FragmentMapBinding
import com.example.googlemap.model.EventObserver
import com.example.googlemap.widget.MyCustomView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private lateinit var mapView: MapView

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
                viewModel?.onMapReady(map)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            mapCenterEvent.observe(this@MapFragment, EventObserver {
                if (it) {
                    // Updates the location and zoom of the MapView
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(43.1, -87.9), 10f)
                    googleMap?.animateCamera(cameraUpdate)

                    val iconGestureDetector = IconGenerator(this@MapFragment.context).let { generator ->
                        generator.setContentView(setupCustomView())
                        generator.makeIcon("My Marker")
                    }

                    val markerOptions: MarkerOptions =
                        MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconGestureDetector))
                            .position(LatLng(43.1, -87.9))

//                    googleMap?.setInfoWindowAdapter {
//                        object : GoogleMap.InfoWindowAdapter
//
//                    }

                    googleMap?.addMarker(markerOptions)
                }
            })
        }
    }

    private fun setupCustomView(): MyCustomView? {
        this@MapFragment.context?.let { view ->
            val myCustomView = MyCustomView(view, null, 0)

            myCustomView.setTopTextContent("Top Top")

            myCustomView.setBottomTextContent("Bottom Bottom")

            myCustomView.setBottomTextColor(Color.BLUE)

            myCustomView.setTopTextColor(Color.RED)

            return myCustomView
        } ?: return null
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
