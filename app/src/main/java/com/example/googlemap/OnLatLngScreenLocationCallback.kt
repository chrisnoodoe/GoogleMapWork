package com.example.googlemap

import android.graphics.Point

interface OnLatLngScreenLocationCallback {
    fun onLatLngScreenLocationReady(point: Point)
}