package com.example.googlemap.model

import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Person(
    val name: String,
    val profilePhoto: Int,
    private val mPosition: LatLng
) : ClusterItem {

    override fun getSnippet(): String {
        return ""
    }

    override fun getTitle(): String {
        return ""
    }

    override fun getPosition(): LatLng {
        return mPosition
    }
}