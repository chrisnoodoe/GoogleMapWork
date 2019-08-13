package com.example.googlemap

import com.google.android.gms.maps.model.LatLng

interface OnCameraChangeListener {
    fun onCameraChanged(latLng: LatLng, zoom: Int)
}