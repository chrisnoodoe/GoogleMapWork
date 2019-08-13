package com.example.googlemap

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

interface OnMapMarkerDragListener {
    fun onMapMarkerDragStart(marker: Marker)

    fun onMapMarkerDrag(marker: Marker)

    fun onMapMarkerDragEnd(marker: Marker)

    fun onMapMarkerDragStart(id: Long, latLng: LatLng)

    fun onMapMarkerDrag(id: Long, latLng: LatLng)

    fun onMapMarkerDragEnd(id: Long, latLng: LatLng)
}