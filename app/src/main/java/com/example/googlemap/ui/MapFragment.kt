package com.example.googlemap.ui

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.googlemap.R
import com.example.googlemap.databinding.FragmentMapBinding
import com.example.googlemap.model.EventObserver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

private const val LOCATION_PERMISSION_REQUEST_CODE = 1

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

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap: Bitmap =
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(LOCATION_PERMISSION_REQUEST_CODE)
    private fun enableMyLocation(googleMap: GoogleMap?) {
        // Enable the location layer. Request the location permission if needed.
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        context?.let { context ->
            if (EasyPermissions.hasPermissions(context, *permissions)) {
                googleMap?.isMyLocationEnabled = false

                googleMap?.uiSettings?.isMyLocationButtonEnabled = false

                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful && task.result != null) {
                        val myCurrentLocation = LatLng(task.result?.latitude!!, task.result?.longitude!!)

                        generateMyLocationMarker(googleMap, myCurrentLocation)

                        // Updates the location and zoom of the MapView
                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(myCurrentLocation, 16f)
                        googleMap?.animateCamera(cameraUpdate)
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

    private fun generateMyLocationMarker(googleMap: GoogleMap?, location: LatLng) {
        val circleOptions: CircleOptions = CircleOptions().apply {
            center(location)
            radius(15.0)
            fillColor(0x32ff6600)
            strokeWidth(0f)
        }
        googleMap?.addCircle(circleOptions)

        val drawable = resources.getDrawable(R.drawable.shape_ring, null)
        val icon = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(drawable))

        val markerOptions: MarkerOptions = MarkerOptions().apply {
            icon(icon)
            position(location)
            anchor(0.5f, 0.5f)
        }

        googleMap?.addMarker(markerOptions)
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
