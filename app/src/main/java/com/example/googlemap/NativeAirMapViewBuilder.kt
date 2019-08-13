package com.example.googlemap

class NativeAirMapViewBuilder : AirMapViewBuilder<MapFragment, GoogleMapOptions> {

    var options: GoogleMapOptions? = null

    override fun withOptions(options: GoogleMapOptions?): AirMapViewBuilder<MapFragment, GoogleMapOptions> {
        this.options = options
        return this
    }

    override fun build(): MapFragment {

        options?.let {
            return MapFragment.newInstance(it)
        } ?: return MapFragment()

    }

}