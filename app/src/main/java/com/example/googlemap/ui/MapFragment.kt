package com.example.googlemap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.googlemap.R
import com.example.googlemap.databinding.FragmentMapBinding
import com.example.googlemap.model.EventObserver
import com.example.googlemap.model.Person
import com.example.googlemap.model.PersonRenderer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private lateinit var mapView: MapView
    private lateinit var clusterManager: ClusterManager<Person>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this@MapFragment).get(MapViewModel::class.java)

        val binding = FragmentMapBinding.inflate(inflater, container, false).apply {
            viewModel = this@MapFragment.viewModel
            lifecycleOwner = this@MapFragment
            mapView = map

            mapView.onCreate(savedInstanceState)

            mapView.getMapAsync { map ->
                viewModel?.onMapReady(map)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            mapCenterEvent.observe(this@MapFragment, EventObserver {
                if (it) {
                    googleMap?.let { map ->
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    51.503186,
                                    -0.126446
                                ), 9.5f
                            )
                        )
                        clusterManager = ClusterManager(this@MapFragment.context, map)
                        clusterManager.renderer =
                            PersonRenderer(this@MapFragment.context!!, map, clusterManager)

                        map.setOnCameraIdleListener(clusterManager)
                        map.setOnMarkerClickListener(clusterManager)
                        map.setOnInfoWindowClickListener(clusterManager)

                        addItems()
                        clusterManager.cluster()
                    }
                }
            })
        }
    }

    private fun addItems() {
        // http://www.flickr.com/photos/sdasmarchives/5036248203/
        clusterManager.addItem(Person("Walter", R.drawable.walter, viewModel.position()))

        // http://www.flickr.com/photos/usnationalarchives/4726917149/
        clusterManager.addItem(Person("Gran", R.drawable.gran, viewModel.position()))

        // http://www.flickr.com/photos/nypl/3111525394/
        clusterManager.addItem(Person("Ruth", R.drawable.ruth, viewModel.position()))

        // http://www.flickr.com/photos/smithsonian/2887433330/
        clusterManager.addItem(Person("Stefan", R.drawable.stefan, viewModel.position()))

        // http://www.flickr.com/photos/library_of_congress/2179915182/
        clusterManager.addItem(Person("Mechanic", R.drawable.mechanic, viewModel.position()))

        // http://www.flickr.com/photos/nationalmediamuseum/7893552556/
        clusterManager.addItem(Person("Yeats", R.drawable.yeats, viewModel.position()))

        // http://www.flickr.com/photos/sdasmarchives/5036231225/
        clusterManager.addItem(Person("John", R.drawable.john, viewModel.position()))

        // http://www.flickr.com/photos/anmm_thecommons/7694202096/
        clusterManager.addItem(Person("Trevor the Turtle", R.drawable.turtle, viewModel.position()))

        // http://www.flickr.com/photos/usnationalarchives/4726892651/
        clusterManager.addItem(Person("Teach", R.drawable.teacher, viewModel.position()))
    }

    override fun onResume() {
        super.onResume()

        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()

        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()

        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()

        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()

        mapView.onLowMemory()
    }

    companion object {
        @JvmStatic
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}
