package com.example.googlemap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: MapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            fragment = MapFragment()

            supportFragmentManager.inTransaction {
                add(R.id.fragment_container, fragment)
            }
        } else {
            fragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as MapFragment
        }
    }
}
