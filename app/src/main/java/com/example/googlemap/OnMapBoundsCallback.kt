package com.example.googlemap

import com.google.android.gms.maps.model.LatLngBounds

interface OnMapBoundsCallback {
    fun onMapBoundsReady(bounds: LatLngBounds)
}
