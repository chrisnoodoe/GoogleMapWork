package com.example.googlemap.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class CMarker(position: LatLng, name: String, pictureResource: Int) : ClusterItem {

    var name: String = name
    var marker: Int = pictureResource
    var mPosition: LatLng = position
    var mTitle: String? = null
    var mSnippet: String? = null

    override fun getSnippet(): String {
        return mSnippet ?: ""
    }

    override fun getTitle(): String {
        return mTitle ?: ""
    }

    override fun getPosition(): LatLng {
        return mPosition
    }
}