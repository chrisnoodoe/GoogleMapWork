package com.example.googlemap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googlemap.model.Event
import com.google.android.gms.maps.GoogleMap

class MapViewModel : ViewModel() {

    private val _mapCenterEvent = MutableLiveData<Event<Boolean>>()
    val mapCenterEvent: LiveData<Event<Boolean>>
        get() = _mapCenterEvent

    var googleMap: GoogleMap? = null

    init {

    }

    fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        _mapCenterEvent.postValue(Event(true))
    }
}