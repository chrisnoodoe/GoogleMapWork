package com.example.googlemap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googlemap.model.Event
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import java.util.*

class MapViewModel : ViewModel() {

    private val _mapCenterEvent = MutableLiveData<Event<Boolean>>()
    val mapCenterEvent: LiveData<Event<Boolean>>
        get() = _mapCenterEvent

    private val seed = Random(1984)

    var googleMap: GoogleMap? = null

    fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        _mapCenterEvent.postValue(Event(true))
    }

    fun position(): LatLng {
        return LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683))
    }

    private fun random(min: Double, max: Double): Double {
        return seed.nextDouble() * (max - min) + min
    }
}