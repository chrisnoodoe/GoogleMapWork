package com.example.googlemap

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

interface AirMapInterface {

    val CIRCLE_FILL_COLOR: Int
        get() = -0xff2e3f
    val CIRCLE_BORDER_COLOR: Int
        get() = -0x1000000
    val CIRCLE_BORDER_WIDTH: Int
        get() = 0

    /** @return true if the map is fully loaded/initialized.
     */
    abstract fun isInitialized(): Boolean

    /** Clear all markers from the map  */
    abstract fun clearMarkers()

    /**
     * Add the given marker to the map
     *
     * @param marker [AirMapMarker] instance to add
     */
    abstract fun addMarker(marker: AirMapMarker<*>)

    /**
     * Move the marker to the given coordinates
     *
     * @param marker [AirMapMarker] instance to move
     * @param to [LatLng] new destination of the marker
     */
    abstract fun moveMarker(marker: AirMapMarker<*>, to: LatLng)

    /**
     * Remove the given marker from the map
     *
     * @param marker [AirMapMarker] instance to remove
     */
    abstract fun removeMarker(marker: AirMapMarker<*>)

    /**
     * Set the callback for info window click events
     *
     * @param listener [com.airbnb.android.airmapview.listeners.OnInfoWindowClickListener]
     * instance
     */
    abstract fun setOnInfoWindowClickListener(listener: OnInfoWindowClickListener)

    /**
     * Specific to Google Play Services maps. Sets the [GoogleMap.InfoWindowAdapter] and [ ]
     */
    abstract fun setInfoWindowCreator(adapter: GoogleMap.InfoWindowAdapter, creator: InfoWindowCreator)

    /** Draw a circle at the given LatLng, with the given radius  */
    abstract fun drawCircle(latLng: LatLng, radius: Int)

    /** Draw a circle at the given LatLng, with the given radius and stroke width  */
    abstract fun drawCircle(latLng: LatLng, radius: Int, borderColor: Int)

    /** Draw a circle at the given LatLng, with the given radius, stroke width, and stroke color  */
    abstract fun drawCircle(latLng: LatLng, radius: Int, borderColor: Int, borderWidth: Int)

    /**
     * Draw a circle at the given LatLng, with the given radius, stroke width, stroke and fill colors
     */
    abstract fun drawCircle(latLng: LatLng, radius: Int, borderColor: Int, borderWidth: Int, fillColor: Int)

    /**
     * Returns the map screen bounds to the supplied
     * [com.airbnb.android.airmapview.listeners.OnMapBoundsCallback]
     */
    abstract fun getMapScreenBounds(callback: OnMapBoundsCallback)

    /**
     * Returns the point coordinates of the LatLng in the container to the supplied
     * [OnLatLngScreenLocationCallback]
     */
    abstract fun getScreenLocation(latLng: LatLng, callback: OnLatLngScreenLocationCallback)

    /** Sets the given [LatLngBounds] on the map with the specified padding  */
    abstract fun setCenter(latLngBounds: LatLngBounds, boundsPadding: Int)

    /** Set the map zoom level  */
    abstract fun setZoom(zoom: Int)

    /**
     * Animate the map to center the given [LatLng]. Web maps will currently only center the map
     * (no animation).
     */
    abstract fun animateCenter(latLng: LatLng)

    /** Center the map to the given [LatLng]  */
    abstract fun setCenter(latLng: LatLng)

    /** @return [LatLng] of the center of the map
     */
    abstract fun getCenter(): LatLng

    /** @return the zoom level of the map
     */
    abstract fun getZoom(): Int

    /** Register a callback to be invoked when the camera of the map has changed  */
    abstract fun setOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener)

    abstract fun setOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener)

    /**
     * Set the center of the map, and zoom level
     *
     * @param latLng the [LatLng] to set as center
     * @param zoom the zoom level
     */
    abstract fun setCenterZoom(latLng: LatLng, zoom: Int)

    /**
     * Animate the center of the map to the given location and zoom level
     *
     * @param latLng the [LatLng] to animate to center
     * @param zoom the zoom level
     */
    abstract fun animateCenterZoom(latLng: LatLng, zoom: Int)

    /**
     * Register a callback to be invoked when a map marker is clicked
     *
     * @param listener [com.airbnb.android.airmapview.listeners.OnMapMarkerClickListener]
     * callback
     */
    abstract fun setOnMarkerClickListener(listener: OnMapMarkerClickListener)

    /**
     * Register a callback to be invoked when a map marker is dragged
     *
     * @param listener [com.airbnb.android.airmapview.listeners.OnMapMarkerDragListener]
     * callback
     */
    abstract fun setOnMarkerDragListener(listener: OnMapMarkerDragListener)

    /**
     * Register a callback to be invoked when the map is clicked
     *
     * @param listener [com.airbnb.android.airmapview.listeners.OnMapClickListener] callback
     */
    abstract fun setOnMapClickListener(listener: OnMapClickListener)

    /** Set the map's padding. Currently only works with Google Play Services maps.  */
    abstract fun setPadding(left: Int, top: Int, right: Int, bottom: Int)

    /** Enable an indicator for the user's location on the map.  */
    abstract fun setMyLocationEnabled(enabled: Boolean)

    /** Check if the user location is being tracked and shown on te map.  */
    abstract fun isMyLocationEnabled(): Boolean

    /** Enable a button for centering on user location button. Works with GooglePlay Services maps.  */
    abstract fun setMyLocationButtonEnabled(enabled: Boolean)

    /** Enable a toolbar that displays various context-dependent actions.  */
    abstract fun setMapToolbarEnabled(enabled: Boolean)

//    /**
//     * Add the given polyline to the map
//     *
//     * @param polyline [AirMapPolyline] instance to add
//     */
//    abstract fun <T> addPolyline(polyline: AirMapPolyline<T>)
//
//    /**
//     * Remove the given [AirMapPolyline]
//     *
//     * @param polyline the [AirMapPolyline] to remove
//     */
//    abstract fun <T> removePolyline(polyline: AirMapPolyline<T>)
//
//    /** Sets the type of map tiles that should be displayed  */
//    abstract fun setMapType(type: MapType)
//
//    /**
//     * Getting called when runtime location permissions got granted. Any action needing location
//     * permissions should be executed here.
//     */
//    abstract fun onLocationPermissionsGranted()
//
//    /**
//     * Add the given polygon to the map
//     *
//     * @param polygon [AirMapPolygon] instance to add
//     */
//    abstract fun <T> addPolygon(polygon: AirMapPolygon<T>)
//
//    /**
//     * Remove the given [AirMapPolygon]
//     *
//     * @param polygon the [AirMapPolygon] to remove
//     */
//    abstract fun <T> removePolygon(polygon: AirMapPolygon<T>)
//
//    /**
//     * Adds a GeoJson layer to the map. Currently only supports adding one layer.
//     * Note: this layer is automatically removed when the map view is destroyed.
//     *
//     * @param layer An [AirMapGeoJsonLayer] layer with GeoJson and optional styling attributes
//     */
//    @Throws(JSONException::class)
//    abstract fun setGeoJsonLayer(layer: AirMapGeoJsonLayer)
//
//    /** Remove GeoJson layer from map, if any.  */
//    abstract fun clearGeoJsonLayer()
//
//    /** Get a Bitmap snapshot of the current  */
//    abstract fun getSnapshot(listener: OnSnapshotReadyListener)
}