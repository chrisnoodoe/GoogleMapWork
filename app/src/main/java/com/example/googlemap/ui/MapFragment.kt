package com.example.googlemap.ui

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.googlemap.R
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

                    // Setup Sample Markers
                    generateSamples(googleMap)

                    val adapter = InfoWindowType1Adapter(this@MapFragment.requireActivity())
                    googleMap?.setInfoWindowAdapter(adapter)
                }
            })
        }
    }

    private fun generateSamples(googleMap: GoogleMap?) {
        googleMap?.addMarker(generateType1Marker())
        googleMap?.addMarker(gernerateType2Marker())
        googleMap?.addMarker(generateType3Marker())
        googleMap?.addMarker(generateType4Marker())
        googleMap?.addMarker(generateType5Marker())
    }

    private fun generateType1Marker(): MarkerOptions {
        val bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.noodoe)
        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        return MarkerOptions().icon(icon).position(LatLng(43.1, -87.9)).title("1")
    }

    private fun generateType3Marker(): MarkerOptions {
        val bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.unavailable)
        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        return MarkerOptions().icon(icon).position(LatLng(43.1, -87.8)).title("2")
    }

    private fun generateType4Marker(): MarkerOptions {
        val bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.invalid_name)
        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        return MarkerOptions().icon(icon).position(LatLng(43.0, -87.8)).title("3")
    }

    private fun generateType5Marker(): MarkerOptions {
        val bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.invalid_name)
        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        return MarkerOptions().icon(icon).position(LatLng(42.9, -87.8)).title("5")
    }

    private fun gernerateType2Marker(): MarkerOptions {
        val iconGestureDetector = IconGenerator(this@MapFragment.context).let { generator ->
            generator.setContentView(setupCustomView())
            generator.makeIcon("My Marker")
        }

        return MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconGestureDetector))
            .position(LatLng(43.2, -87.9)).title("4")
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
