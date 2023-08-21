@file:Suppress("DEPRECATION")

package com.example.location_setting

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.location_setting.databinding.ActivityMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {
    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val providerClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(object : ConnectionCallbacks {
                override fun onConnected(p0: Bundle?) {
                    if(ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.ACCESS_FINE_LOCATION)===PackageManager.PERMISSION_GRANTED){
                        providerClient.lastLocation.addOnSuccessListener(
                            this@MainActivity,
                            object:OnSuccessListener<Location>{
                                override fun onSuccess(location:Location?) {
                                    val latitude=location?.latitude
                                    val longitude=location?.longitude
                                    Log.d("kk","$latitude,$longitude")
                                }
                            }
                        )
                    }
                    else{
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
                        Toast.makeText(this@MainActivity,"권한이 거부되었습니다",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onConnectionSuspended(p0: Int) {
                    Log.d("kk", "위치 획득 불가")
                }
            })
            .addOnConnectionFailedListener(object : OnConnectionFailedListener {
                override fun onConnectionFailed(p0: ConnectionResult) {
                    Log.d("kk", "사용할 수 있는 위치 제공자가 없습니다.")
                }
            })
            .build()
    }
}