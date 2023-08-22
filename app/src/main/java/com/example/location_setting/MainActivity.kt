
package com.example.location_setting

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.location_setting.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityMainBinding
    lateinit var providerClient: FusedLocationProviderClient
    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (supportFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?)!!.getMapAsync(this)
        providerClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        UpdateLocation().onConnected()
    }

    inner class UpdateLocation : LocationCallback() {
        fun onConnected() {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                providerClient.getLastLocation().addOnSuccessListener(
                    this@MainActivity,
                    object : OnSuccessListener<Location> {
                        override fun onSuccess(p0: Location?) {
                            if (p0 != null) {
                                val latitude = p0.latitude
                                val longitude = p0.longitude
                                val latLng = LatLng(latitude, longitude)
                                val position = CameraPosition.Builder()
                                    .target(latLng)
                                    .zoom(16f)
                                    .build()
                                googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(position))
                            } else {
                                Toast.makeText(this@MainActivity,"위치를 찾을 수 없습니다",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                )
            }
        }
    }
}
