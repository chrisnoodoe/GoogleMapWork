package com.example.googlemap.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.googlemap.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class InfoWindowType1Adapter(context: Context) : GoogleMap.InfoWindowAdapter {

    private var context = context

    override fun getInfoContents(marker: Marker?): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker?): View? {

        when(marker?.title) {
            "1" -> {
                val view = (context as Activity).layoutInflater.inflate(R.layout.view_info_window_type1, null)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvStatus = view.findViewById<TextView>(R.id.tv_status)
                tvTitle.text = "J-1771"
                tvStatus.text = "1 Available"

                return view
            }
            "2" -> {
                val view = (context as Activity).layoutInflater.inflate(R.layout.view_info_window_type2, null)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvStatus = view.findViewById<TextView>(R.id.tv_status)
                tvTitle.text = "SAE Combo"
                tvStatus.text = "x 1"

                return view
            }
            "3" -> {
                val view = (context as Activity).layoutInflater.inflate(R.layout.view_info_window_type3, null)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvStatus = view.findViewById<TextView>(R.id.tv_status)
                tvTitle.text = "J-1773"
                tvStatus.text = "3 Available"

                return view
            }
            "4" -> {
                val view = (context as Activity).layoutInflater.inflate(R.layout.view_info_window_type4, null)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvStatus = view.findViewById<TextView>(R.id.tv_status)
                tvTitle.text = "J-1774"
                tvStatus.text = "4 Available"

                return view
            }
            "5" -> {
                val view = (context as Activity).layoutInflater.inflate(R.layout.view_info_window_type5, null)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvStatus = view.findViewById<TextView>(R.id.tv_status)
//                tvTitle.text = "J-1774"
//                tvStatus.text = "4 Available"

                return view
            }
        }

        return null
    }

}