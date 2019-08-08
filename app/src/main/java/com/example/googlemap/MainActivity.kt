package com.example.googlemap

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.googlemap.model.MyItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager

class MainActivity : FragmentActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    private var mClusterManager: ClusterManager<MyItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        googleMap?.let {
            this.googleMap = googleMap

            startDemo()
        }
    }

    private fun createMarker(context: Context, point: LatLng, bedroomCount: Int): MarkerOptions {
        val marker = MarkerOptions()
        marker.position(point)
//        val px = context.resources.getDimensionPixelSize(R.dimen.map_marker_diameter)
//        val markerView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
//            R.layout.map_circle_text,
//            null
//        )
        val markerView = layoutInflater.inflate(R.layout.map_circle_text, null)
        markerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        markerView.layout(0, 0, 100, 100)
        markerView.buildDrawingCache()
        val bedNumberTextView = markerView.findViewById(R.id.bed_num_text_view) as TextView
        val mDotMarkerBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(mDotMarkerBitmap)
        bedNumberTextView.setText(bedroomCount)
        markerView.draw(canvas)
        marker.icon(BitmapDescriptorFactory.fromBitmap(mDotMarkerBitmap))
        return marker
    }

    private fun startDemo() {
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-33.8696, 151.2094), 10f))

        googleMap?.addMarker(createMarker(this, LatLng(-33.8696, 151.2094), 5))
    }
}
