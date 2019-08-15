package com.example.googlemap.ui

import android.Manifest
import android.annotation.SuppressLint
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

private const val LOCATION_PERMISSION_REQUEST_CODE = 1
private const val REQUEST_PERMISSIONS_REQUEST_CODE = 34

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private lateinit var mapView: MapView
    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

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

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            mapCenterEvent.observe(this@MapFragment, EventObserver {
                if (it) {
                    enableMyLocation(googleMap)
                }
            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(LOCATION_PERMISSION_REQUEST_CODE)
    private fun enableMyLocation(googleMap: GoogleMap?) {
        // Enable the location layer. Request the location permission if needed.
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        context?.let { context ->
            if (EasyPermissions.hasPermissions(context, *permissions)) {
                googleMap?.isMyLocationEnabled = true

                googleMap?.uiSettings?.isMyLocationButtonEnabled = false

                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful && task.result != null) {
                        val myCurrentLocation = LatLng(task.result?.latitude!!, task.result?.longitude!!)

                        val builder = CameraPosition.Builder()
                        builder.zoom(15f)
                        builder.target(myCurrentLocation)

                        googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()))

//                        googleMap?.addMarker(generateMarker(myCurrentLocation))

                        // Updates the location and zoom of the MapView
//                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(myCurrentLocation, 16f)
//                        googleMap?.animateCamera(cameraUpdate)
                    }
                }
            } else {
                // if permissions are not currently granted, request permissions
                EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_rationale_location),
                    LOCATION_PERMISSION_REQUEST_CODE,
                    *permissions
                )
            }
        }
    }

    private fun generateSamples(googleMap: GoogleMap?) {
        googleMap?.addMarker(generateType1Marker())
        googleMap?.addMarker(gernerateType2Marker())
        googleMap?.addMarker(generateType3Marker())
        googleMap?.addMarker(generateType4Marker())
    }

    private fun generateMarker(location: LatLng): MarkerOptions {
        val bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.noodoe)
        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        return MarkerOptions().icon(icon).position(location).title("1")
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
