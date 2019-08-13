package com.example.googlemap

import com.google.android.gms.maps.model.LatLng

interface OnMapClickListener {
    fun onMapClick(latLng: LatLng)
}
