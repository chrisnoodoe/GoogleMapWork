package com.example.googlemap.util

import com.example.googlemap.model.MyItem
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONException
import java.io.InputStream
import java.util.*

class MyItemReader {

    /*
     * This matches only once in whole input,
     * so Scanner.next returns whole InputStream as a String.
     * http://stackoverflow.com/a/5445161/2183804
     */
    private val REGEX_INPUT_BOUNDARY_BEGINNING = "\\A"

    @Throws(JSONException::class)
    fun read(inputStream: InputStream): List<MyItem> {
        val items = ArrayList<MyItem>()
        val json = Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next()
        val array = JSONArray(json)
        for (i in 0 until array.length()) {
            var title: String? = null
            var snippet: String? = null
            val `object` = array.getJSONObject(i)
            val lat = `object`.getDouble("lat")
            val lng = `object`.getDouble("lng")
            if (!`object`.isNull("title")) {
                title = `object`.getString("title")
            }
            if (!`object`.isNull("snippet")) {
                snippet = `object`.getString("snippet")
            }

            items.add(MyItem(LatLng(lat, lng), title, snippet))
        }
        return items
    }
}