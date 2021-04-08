package com.juno.cities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val saoPaulo = LatLng(-23.550520, -46.633308)
        val beloHorizonte = LatLng(-19.917299, -43.934559)
        val recife = LatLng(-8.052240, -34.928612)
        val rio = LatLng(-22.906847, -43.172897)
        val salvador = LatLng(-12.974722, -38.476665)
        val foz = LatLng(-25.516592, -54.585251)


        mMap.addMarker(MarkerOptions().position(saoPaulo).title("São Paulo"))
        mMap.addMarker(MarkerOptions().position(beloHorizonte).title("Belo Horizonte"))
        mMap.addMarker(MarkerOptions().position(recife).title("Recife"))
        mMap.addMarker(MarkerOptions().position(rio).title("Rio de Janeiro"))
        mMap.addMarker(MarkerOptions().position(salvador).title("Salvador"))
        mMap.addMarker(MarkerOptions().position(foz).title("Foz do Iguaçu"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(recife))
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) );

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.setAllGesturesEnabled(true)
    }
}