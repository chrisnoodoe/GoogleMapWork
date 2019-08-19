package com.example.googlemap.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.example.googlemap.R
import com.example.googlemap.model.CMarker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import java.util.*

class DonutChartRenderer(context: Context, map: GoogleMap, clusterManager: ClusterManager<CMarker>) :
    DefaultClusterRenderer<CMarker>(context, map, clusterManager) {

    private val context = context

    private var names: Array<String>? = null

    private var colorSets = intArrayOf(
        context.getColor(R.color.circleBlue),
        context.getColor(R.color.circlePurple),
        context.getColor(R.color.circleOrange)
    )

    fun colors(colors: IntArray) {
        colorSets = colors.copyOf(colors.size)
    }

    fun names(nameList: Array<String>) {
        this.names = nameList.copyOf()
    }

    private fun values(cluster: Cluster<CMarker>): FloatArray {
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
            caCluster.size > 50
        } ?: false
    }

    override fun onBeforeClusterRendered(cluster: Cluster<CMarker>, markerOptions: MarkerOptions?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val donutChart = inflater.inflate(R.layout.donutchart_cluster, null)

        val backgroundImageView: ImageView? = donutChart.findViewById(R.id.iv_background)
        val contentTextView: TextView = donutChart.findViewById(R.id.tv_content)

        val mClusterIconGenerator = IconGenerator(context)
        mClusterIconGenerator.setContentView(donutChart)

        val backgroundColor: Drawable
        backgroundColor = ColorDrawable(Color.TRANSPARENT)
        mClusterIconGenerator.setBackground(backgroundColor)

        val chart = DonutChart(values(cluster), colorSets)
        backgroundImageView?.setImageDrawable(chart)
        contentTextView.text = cluster.size.toString()

        val icon = mClusterIconGenerator.makeIcon(cluster.size.toString() + "")
        val bitmap = BitmapDescriptorFactory.fromBitmap(icon)

        markerOptions?.apply {
            icon(bitmap)
            anchor(.5f, .5f)
        }
    }
}