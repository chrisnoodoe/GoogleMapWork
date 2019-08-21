package com.example.googlemap.model

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import com.example.googlemap.R
import com.example.googlemap.util.MultiDrawable
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import java.util.*
import kotlin.math.min

/**
 * Draws profile photos inside markers (using IconGenerator).
 * When there are multiple people in the cluster, draw multiple photos (using MultiDrawable).
 */
class PersonRenderer(
    context: Context,
    googleMap: GoogleMap,
    clusterManager: ClusterManager<Person>?
) :
    DefaultClusterRenderer<Person>(context, googleMap, clusterManager) {
    private val mIconGenerator = IconGenerator(context)
    private val mClusterIconGenerator = IconGenerator(context)
    private val mImageView: ImageView
    private val mClusterImageView: ImageView
    private val mDimension: Int
    private val context: Context = context

    init {
        val activity = context as Activity
        val multiProfile = activity.layoutInflater.inflate(R.layout.multi_profile, null)
        mClusterIconGenerator.setContentView(multiProfile)
        mClusterImageView = multiProfile.findViewById(R.id.image)

        mImageView = ImageView(context)
        mDimension = context.resources.getDimension(R.dimen.custom_profile_image).toInt()
        mImageView.layoutParams = ViewGroup.LayoutParams(mDimension, mDimension)
        val padding = context.resources.getDimension(R.dimen.custom_profile_padding).toInt()
        mImageView.setPadding(padding, padding, padding, padding)
        mIconGenerator.setContentView(mImageView)
    }

    override fun onBeforeClusterItemRendered(person: Person?, markerOptions: MarkerOptions?) {
        // Draw a single person.
        // Set the info window to show their name.
        mImageView.setImageResource(person!!.profilePhoto)
        val icon = mIconGenerator.makeIcon()
        markerOptions!!.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(person.name)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Person>, markerOptions: MarkerOptions) {

        // Draw multiple people.
        // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
        val profilePhotos = ArrayList<Drawable>(min(4, cluster.size))
        val width = mDimension
        val height = mDimension

        for ((_, profilePhoto) in cluster.items) {
            // Draw 4 at most.
            if (profilePhotos.size == 4) break
            val drawable = context.resources.getDrawable(profilePhoto, null)
            drawable.setBounds(0, 0, width, height)
            profilePhotos.add(drawable)
        }
        val multiDrawable = MultiDrawable(profilePhotos)
        multiDrawable.setBounds(0, 0, width, height)

        mClusterImageView.setImageDrawable(multiDrawable)
        val icon = mClusterIconGenerator.makeIcon(cluster.size.toString())
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Person>?): Boolean {
        // Always render clusters.
        cluster?.let {
            return it.size > 1
        } ?: return false
    }
}