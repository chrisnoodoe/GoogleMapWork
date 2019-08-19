package com.example.googlemap

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.example.googlemap.model.CMarker
import com.example.googlemap.widget.DonutChartRenderer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : FragmentActivity(), OnMapReadyCallback,
    ClusterManager.OnClusterClickListener<CMarker>,
    ClusterManager.OnClusterInfoWindowClickListener<CMarker>,
    ClusterManager.OnClusterItemClickListener<CMarker>,
    ClusterManager.OnClusterItemInfoWindowClickListener<CMarker> {

    private var googleMap: GoogleMap? = null
    private var mClusterManager: ClusterManager<CMarker>? = null

    private val mRandom = Random(1984)
    private val r = Random()
    private var chart: DonutChartRenderer? = null
    private var names = arrayOf("Toyota", "Ford", "Honda")
    private val markerIcon = arrayOf("red", "green", "blue")
    private var colorSets: IntArray = intArrayOf()
    private var location = LatLngBounds(LatLng(35.607781, 51.187924), LatLng(35.778940, 51.548771))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        (map as SupportMapFragment).getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()

        colorSets = intArrayOf(
            getColor(R.color.circleBlue),
            getColor(R.color.circlePurple),
            getColor(R.color.circleOrange)
        )
    }

    override fun onResume() {
        super.onResume()

        (map as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        googleMap?.let {
            this.googleMap = googleMap

            startDemo()
        }
    }

    private fun startDemo() {

        googleMap?.setOnMapLoadedCallback {
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(location, 0))
        }

        googleMap?.let { map ->
            mClusterManager = ClusterManager(this, map)

            mClusterManager?.let { manager ->
                chart = DonutChartRenderer(applicationContext, map, manager)

                chart?.colors(colorSets)
                chart?.names(names)

                mClusterManager?.renderer = chart

                layoutInflater
                googleMap?.setOnCameraIdleListener(mClusterManager)
                googleMap?.setOnMarkerClickListener(mClusterManager)
                googleMap?.setOnInfoWindowClickListener(mClusterManager)
                mClusterManager?.setOnClusterClickListener(this)
                mClusterManager?.setOnClusterInfoWindowClickListener(this)
                mClusterManager?.setOnClusterItemClickListener(this)
                mClusterManager?.setOnClusterItemInfoWindowClickListener(this)
                addItems()
                mClusterManager?.cluster()
            }
        }
    }

    private fun addItems() {
        for (i in 0..299) {
            val rand = r.nextInt(3)

            val marker = CMarker(randomPosition(), names[rand], getDrawableId(markerIcon[rand]))
            marker.mTitle = names[rand]
            mClusterManager?.addItem(marker)
        }
    }

    private fun getDrawableId(name: String): Int {
        try {
            val field = R.drawable::class.java.getField("marker_$name")
            return field.getInt(null)
        } catch (e: Exception) {
            //
        }

        return -1
    }

    private fun randomPosition(): LatLng {
        val minLatitude = location.southwest.latitude
        val maxLatitude = location.northeast.latitude
        val minLongitude = location.southwest.longitude
        val maxLongitude = location.northeast.longitude
        return LatLng(
            minLatitude + (maxLatitude - minLatitude) * mRandom.nextDouble(),
            minLongitude + (maxLongitude - minLongitude) * mRandom.nextDouble()
        )
    }

    override fun onClusterClick(cluster: Cluster<CMarker>?): Boolean {

        cluster?.let { cluster ->
            val builder = LatLngBounds.builder()

            for (item in cluster.items) {
                builder.include(item.position)
            }

            val bounds = builder.build()

            try {
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return true
    }

    override fun onClusterInfoWindowClick(cluster: Cluster<CMarker>?) {
        layoutInflater
    }

    override fun onClusterItemClick(item: CMarker?): Boolean {
        return false
    }

    override fun onClusterItemInfoWindowClick(item: CMarker?) {

    }
}
