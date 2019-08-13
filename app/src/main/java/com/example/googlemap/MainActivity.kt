package com.example.googlemap

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.googlemap.util.processGeoJsonLayer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.data.geojson.GeoJsonFeature
import com.google.maps.android.data.geojson.GeoJsonLayer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity(), OnMapReadyCallback {

    private val loadGeoJsonResult = MutableLiveData<Result<GeoJsonData>>()

    private val _geoJsonLayer = MediatorLiveData<GeoJsonLayer>()
    val geoJsonLayer: LiveData<GeoJsonLayer>
        get() = _geoJsonLayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (map as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val mapPair = Pair(googleMap, R.raw.map_markers)

        val layer = GeoJsonLayer(mapPair.first, mapPair.second, this)

        processGeoJsonLayer(layer, this)
        layer.isLayerOnMap

        val geoJsonData = GeoJsonData(layer, buildFeatureMap(layer))

        println("")
    }

    private fun buildFeatureMap(layer: GeoJsonLayer): Map<String, GeoJsonFeature> {
        val featureMap: MutableMap<String, GeoJsonFeature> = mutableMapOf()
        layer.features.forEach {
            val id = it.getProperty("id")
            if (!TextUtils.isEmpty(id)) {
                // Marker can map to multiple room IDs
                for (part in id.split(",")) {
                    featureMap[part] = it
                }
            }
        }
        return featureMap
    }
}

/** Data loaded by this use case. */
data class GeoJsonData(
    val geoJsonLayer: GeoJsonLayer,
    val featureMap: Map<String, GeoJsonFeature>
)
