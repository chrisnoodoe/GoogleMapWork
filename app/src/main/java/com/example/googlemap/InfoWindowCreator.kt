package com.example.googlemap

import android.view.View

interface InfoWindowCreator {
    fun createInfoWindow(airMarker: AirMapMarker<*>): View
}