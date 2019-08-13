package com.example.googlemap

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.common.config.GservicesValue.isInitialized
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class MyMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    OnCameraChangeListener,
    OnMapClickListener,
    OnMapMarkerDragListener,
    OnMapMarkerClickListener,
    OnMapLoadedListener,
    OnInfoWindowClickListener {

    private val INVALID_ZOOM = -1

    var mapInterface: AirMapInterface? = null
    var onCameraMoveListener: OnCameraMoveListener? = null
    var onCameraChangeListener: OnCameraChangeListener? = null
    var mOnCameraMoveTriggered: Boolean = false
    var onMapInitializedListener: OnMapInitializedListener? = null
    var onMapMarkerDragListener: OnMapMarkerDragListener? = null
    var onMapMarkerClickListener: OnMapMarkerClickListener? = null
    var onMapClickListener: OnMapClickListener? = null
    var onInfoWindowClickListener: OnInfoWindowClickListener? = null

    fun addMarker(marker: AirMapMarker<*>): Boolean {
        if (isInitialized()) {
            mapInterface?.addMarker(marker)
            return true
        }
        return false
    }

    fun animateCenterZoom(latLng: LatLng, zoom: Int): Boolean {
        if (isInitialized()) {
            mapInterface?.animateCenterZoom(latLng, zoom)
            return true
        }
        return false
    }

    /**
     * Used for initialization of the underlying map provider.
     *
     * @param fragmentManager required for initialization
     */
    fun initialize(fragmentManager: FragmentManager) {
        val mapInterface = fragmentManager.findFragmentById(R.id.map_frame) as AirMapInterface?

        if (mapInterface != null) {
            initialize(fragmentManager, mapInterface)
        } else {
            initialize(fragmentManager, DefaultAirMapViewBuilder(context).builder().build())
        }
    }

    fun initialize(fragmentManager: FragmentManager?, mapInterface: AirMapInterface?) {
        if (mapInterface == null || fragmentManager == null) {
            throw IllegalArgumentException("Either mapInterface or fragmentManager is null")
        }

        this.mapInterface = mapInterface
        this.mapInterface?.setOnMapLoadedListener(this)

        fragmentManager.beginTransaction()
            .replace(id, this.mapInterface as Fragment)
            .commit()

        fragmentManager.executePendingTransactions()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_MOVE) {
            if (onCameraMoveListener != null && !mOnCameraMoveTriggered) {
                onCameraMoveListener?.onCameraMove()
                mOnCameraMoveTriggered = true
            }
        } else if (ev.action == MotionEvent.ACTION_UP) {
            mOnCameraMoveTriggered = false
        }

        return super.dispatchTouchEvent(ev)
    }

    override fun onMapMarkerDragStart(marker: Marker) {
        onMapMarkerDragListener?.onMapMarkerDragStart(marker)
    }

    override fun onMapMarkerDrag(marker: Marker) {
        onMapMarkerDragListener?.onMapMarkerDrag(marker)
    }

    override fun onMapMarkerDragEnd(marker: Marker) {
        onMapMarkerDragListener?.onMapMarkerDragEnd(marker)
    }

    override fun onMapMarkerDragStart(id: Long, latLng: LatLng) {
        onMapMarkerDragListener?.onMapMarkerDragStart(id, latLng)
    }

    override fun onMapMarkerDrag(id: Long, latLng: LatLng) {
        onMapMarkerDragListener?.onMapMarkerDrag(id, latLng)
    }

    override fun onMapMarkerDragEnd(id: Long, latLng: LatLng) {
        onMapMarkerDragListener?.onMapMarkerDragEnd(id, latLng)
    }

    override fun onCameraChanged(latLng: LatLng, zoom: Int) {
        onCameraChangeListener?.onCameraChanged(latLng, zoom)
    }

    override fun onMapClick(latLng: LatLng) {
        onMapClickListener?.onMapClick(latLng)
    }

    override fun onMapMarkerClick(airMarker: AirMapMarker<*>): Boolean {
        return onMapMarkerClickListener?.onMapMarkerClick(airMarker) ?: false
    }

    override fun onMapLoaded() {
        if (isInitialized()) {
            mapInterface?.setOnCameraChangeListener(this)
            mapInterface?.setOnMapClickListener(this)
            mapInterface?.setOnMarkerClickListener(this)
            mapInterface?.setOnMarkerDragListener(this)
            mapInterface?.setOnInfoWindowClickListener(this)

            onMapInitializedListener?.onMapInitialized()
        }
    }

    override fun onInfoWindowClick(marker: AirMapMarker<*>) {
        onInfoWindowClickListener?.onInfoWindowClick(marker)
    }

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.map_view, this)
    }
}