package com.example.googlemap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class MapFragment : SupportMapFragment(), AirMapInterface {

    private var onMapLoadedListener: OnMapLoadedListener? = null

    /** @return true if the map is fully loaded/initialized.
     */
    override fun isInitialized(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Clear all markers from the map  */
    override fun clearMarkers() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Add the given marker to the map
     *
     * @param marker [AirMapMarker] instance to add
     */
    override fun addMarker(marker: AirMapMarker<*>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Move the marker to the given coordinates
     *
     * @param marker [AirMapMarker] instance to move
     * @param to [LatLng] new destination of the marker
     */
    override fun moveMarker(marker: AirMapMarker<*>, to: LatLng) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Remove the given marker from the map
     *
     * @param marker [AirMapMarker] instance to remove
     */
    override fun removeMarker(marker: AirMapMarker<*>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Set the callback for info window click events
     *
     * @param listener [com.airbnb.android.airmapview.listeners.OnInfoWindowClickListener]
     * instance
     */
    override fun setOnInfoWindowClickListener(listener: OnInfoWindowClickListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Specific to Google Play Services maps. Sets the [GoogleMap.InfoWindowAdapter] and [ ]
     */
    override fun setInfoWindowCreator(adapter: GoogleMap.InfoWindowAdapter, creator: InfoWindowCreator) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Draw a circle at the given LatLng, with the given radius  */
    override fun drawCircle(latLng: LatLng, radius: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Draw a circle at the given LatLng, with the given radius and stroke width  */
    override fun drawCircle(latLng: LatLng, radius: Int, borderColor: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Draw a circle at the given LatLng, with the given radius, stroke width, and stroke color  */
    override fun drawCircle(latLng: LatLng, radius: Int, borderColor: Int, borderWidth: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Draw a circle at the given LatLng, with the given radius, stroke width, stroke and fill colors
     */
    override fun drawCircle(latLng: LatLng, radius: Int, borderColor: Int, borderWidth: Int, fillColor: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Returns the map screen bounds to the supplied
     * [com.airbnb.android.airmapview.listeners.OnMapBoundsCallback]
     */
    override fun getMapScreenBounds(callback: OnMapBoundsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Returns the point coordinates of the LatLng in the container to the supplied
     * [OnLatLngScreenLocationCallback]
     */
    override fun getScreenLocation(latLng: LatLng, callback: OnLatLngScreenLocationCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Sets the given [LatLngBounds] on the map with the specified padding  */
    override fun setCenter(latLngBounds: LatLngBounds, boundsPadding: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Set the map zoom level  */
    override fun setZoom(zoom: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Animate the map to center the given [LatLng]. Web maps will currently only center the map
     * (no animation).
     */
    override fun animateCenter(latLng: LatLng) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Center the map to the given [LatLng]  */
    override fun setCenter(latLng: LatLng) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** @return [LatLng] of the center of the map
     */
    override fun getCenter(): LatLng {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** @return the zoom level of the map
     */
    override fun getZoom(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Register a callback to be invoked when the camera of the map has changed  */
    override fun setOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
        this.onMapLoadedListener = onMapLoadedListener
    }

    /**
     * Set the center of the map, and zoom level
     *
     * @param latLng the [LatLng] to set as center
     * @param zoom the zoom level
     */
    override fun setCenterZoom(latLng: LatLng, zoom: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Animate the center of the map to the given location and zoom level
     *
     * @param latLng the [LatLng] to animate to center
     * @param zoom the zoom level
     */
    override fun animateCenterZoom(latLng: LatLng, zoom: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Register a callback to be invoked when a map marker is clicked
     *
     * @param listener [com.airbnb.android.airmapview.listeners.OnMapMarkerClickListener]
     * callback
     */
    override fun setOnMarkerClickListener(listener: OnMapMarkerClickListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Register a callback to be invoked when a map marker is dragged
     *
     * @param listener [com.airbnb.android.airmapview.listeners.OnMapMarkerDragListener]
     * callback
     */
    override fun setOnMarkerDragListener(listener: OnMapMarkerDragListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Register a callback to be invoked when the map is clicked
     *
     * @param listener [com.airbnb.android.airmapview.listeners.OnMapClickListener] callback
     */
    override fun setOnMapClickListener(listener: OnMapClickListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Set the map's padding. Currently only works with Google Play Services maps.  */
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Enable an indicator for the user's location on the map.  */
    override fun setMyLocationEnabled(enabled: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Check if the user location is being tracked and shown on te map.  */
    override fun isMyLocationEnabled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Enable a button for centering on user location button. Works with GooglePlay Services maps.  */
    override fun setMyLocationButtonEnabled(enabled: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** Enable a toolbar that displays various context-dependent actions.  */
    override fun setMapToolbarEnabled(enabled: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var googleMap: GoogleMap? = null
    var options: GoogleMapOptions? = null
    private var myLocationEnabled: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = super.onCreateView(inflater, container, savedInstanceState)

        getMapAsync { map ->
            if (map != null) {
                this.googleMap = map
                val settings = this.googleMap?.uiSettings

                settings?.isZoomControlsEnabled = false
                settings?.isMyLocationButtonEnabled = false

                this.myLocationEnabled = myLocationEnabled
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(options: GoogleMapOptions) = MapFragment().apply {
            this.options = options
        }
    }
}