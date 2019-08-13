package com.example.googlemap.ui

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap

class MapViewModel : ViewModel() {

    var googleMap: GoogleMap? = null

    init {

    }

    fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        println("")
//        loadGeoJsonFeaturesUseCase(
//            LoadGeoJsonParams(googleMap, R.raw.map_markers),
//            loadGeoJsonResult
//        )
    }
}