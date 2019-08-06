package com.example.googlemap.widget

import android.content.Context
import android.graphics.Color
import com.example.googlemap.model.CMarker
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import java.util.*

open class ChartRenderer(context: Context, map: GoogleMap, clusterManager: ClusterManager<CMarker>) :
    DefaultClusterRenderer<CMarker>(context, map, clusterManager) {

    var mClusterIconGenerator: IconGenerator = IconGenerator(context)
    var COLORS = intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
    private var names: Array<String>? = null

    fun colors(colors: IntArray) {
        COLORS = colors.copyOf(colors.size)
    }

    fun names(nameList: Array<String>) {
        this.names = nameList.copyOf()
    }

    fun values(cluster: Cluster<CMarker>): FloatArray {
        return names?.let { array ->
            val values = FloatArray(array.size)
            Arrays.fill(values, 0f)

            var key: Int

            for (p in cluster.items) {
                key = names?.asList()?.indexOf(p.name)!!

                values[key]++
            }

            values
        } ?: floatArrayOf(0f)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<CMarker>?): Boolean {
        return cluster?.let { caCluster ->
            caCluster.size > 1
        } ?: false
    }
}

