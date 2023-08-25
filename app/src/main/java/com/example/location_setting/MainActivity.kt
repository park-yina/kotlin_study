
package com.example.location_setting

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    lateinit var providerClient: FusedLocationProviderClient
    lateinit var apiClient: GoogleApiClient
    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 권한 확인 및 요청
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("testing", "권한 설정 완료")
                // 위치 권한이 부여된 경우 GoogleApiClient 연결
                apiClient.connect()
            } else {
                Toast.makeText(this, "권한이 설정되지 않았습니다", Toast.LENGTH_LONG).show()
            }
        }

        // Google Map 초기화
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)

        providerClient = LocationServices.getFusedLocationProviderClient(this)
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        // 위치 권한 확인
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onConnected(p0: Bundle?) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            providerClient.lastLocation.addOnSuccessListener(
                this@MainActivity,
                object : OnSuccessListener<Location> {
                    override fun onSuccess(p0: Location?) {
                        p0?.let {
                            val latitude = p0.latitude
                            val longitude = p0.longitude
                            moveMap(latitude, longitude)
                        }
                    }
                }
            )
            apiClient.disconnect()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
    }

    private fun moveMap(latitude: Double, longitude: Double) {
        val latLng = LatLng(latitude, longitude)
        val position = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(position))
        val markerOptions=MarkerOptions()
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        markerOptions.position(latLng)
        googleMap?.addMarker(markerOptions)
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d("testing","연결이 중단되었습니다")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("testing","연결 자체에 실패했습니다")
    }
}