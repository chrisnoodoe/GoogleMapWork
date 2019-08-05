package com.example.googlemap

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.googlemap.model.MyItem
import com.example.googlemap.util.MyItemReader
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import org.json.JSONException

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

    private fun startDemo() {

        googleMap?.let { map ->
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.503186, -0.126446), 10f))
            mClusterManager = ClusterManager(this, map)
            map.setOnCameraIdleListener(mClusterManager)

            try {
                readItems()
            } catch (e: Exception) {
                Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show()
            }
        }
    }

    @Throws(JSONException::class)
    private fun readItems() {
        val inputStream = resources.openRawResource(R.raw.radar_search)
        val items = MyItemReader().read(inputStream)
        mClusterManager?.addItems(items)
    }
}
