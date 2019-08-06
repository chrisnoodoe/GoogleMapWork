package com.example.googlemap.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.ImageView
import com.example.googlemap.R
import com.example.googlemap.model.CMarker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager

class DonutChartRenderer(context: Context, map: GoogleMap, clusterManager: ClusterManager<CMarker>) :
    ChartRenderer(context, map, clusterManager) {

    private var mClusterImageView: ImageView? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val donutChart = inflater.inflate(R.layout.donutchart_cluster, null)
        mClusterIconGenerator.setContentView(donutChart)
        mClusterImageView = donutChart.findViewById(R.id.image)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<CMarker>, markerOptions: MarkerOptions?) {
        val backgroundColor: Drawable
        backgroundColor = ColorDrawable(Color.TRANSPARENT)
        mClusterIconGenerator.setBackground(backgroundColor)
        val chart = DonutChart(values(cluster), COLORS)
        mClusterImageView?.setImageDrawable(chart)
        val icon = mClusterIconGenerator.makeIcon(cluster.size.toString() + "")
        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(icon))?.anchor(.5f, .5f)
    }
}