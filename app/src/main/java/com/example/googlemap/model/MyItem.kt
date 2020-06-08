package com.example.googlemap.model

import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MyItem(
    val mPosition: LatLng,
    var mTitle: String? = null,
    var mSnippet: String? = null
) : ClusterItem {

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