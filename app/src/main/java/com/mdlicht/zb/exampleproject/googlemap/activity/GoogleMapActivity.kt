package com.mdlicht.zb.exampleproject.googlemap.activity

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityGoogleMapBinding
import com.mdlicht.zb.exampleproject.googlemap.common.CustomMarkerRender
import com.mdlicht.zb.exampleproject.googlemap.viewmodel.GoogleMapViewModel
import android.content.DialogInterface
import android.content.Intent


class GoogleMapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityGoogleMapBinding
    lateinit var mapFragment: SupportMapFragment

    companion object {
        const val PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_google_map)
        binding.vm = ViewModelProviders.of(this).get(GoogleMapViewModel::class.java)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Observe click event about 1 Item cluster
        binding.vm!!.clickResult.observe(this, Observer {
            it?.let {
                AlertDialog.Builder(this).setTitle(it.NAME_KOR).setMessage(it.ADD_KOR + '\n' + it.ADD_KOR_ROAD).show()
                    .show()
            }
        })

        // Observe click event about gps(current location)
        binding.vm!!.gpsClick.observe(this, Observer {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // HAS ALL PERMISSION
                startLocationService()
            } else {
                ActivityCompat.requestPermissions(this, it!!, PERMISSION_REQUEST_CODE)
            }
        })
    }

    /**
     * If map is ready, set cluster manager and map on ViewModel
     */
    override fun onMapReady(p0: GoogleMap?) {
        p0?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.566327, 126.977948), 15f))

        binding.vm?.apply {
            clusterManager = ClusterManager(this@GoogleMapActivity, p0)
            clusterManager.renderer = CustomMarkerRender(this@GoogleMapActivity, p0!!, clusterManager)
            map = p0
            map?.setOnCameraIdleListener(clusterManager)
            map?.setOnMarkerClickListener(clusterManager)
            clusterManager.setOnClusterItemClickListener(this)
            clusterManager.setOnClusterClickListener(this)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationService() {
        if (checkGpsSetting()) {
            val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, binding.vm)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, binding.vm)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationService()
                } else {
                    Toast.makeText(this, R.string.google_map_msg_permission_denied, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * If GPS Service is off, start settings activity
     */
    private fun checkGpsSetting(): Boolean {
        val gps = android.provider.Settings.Secure.getString(
            contentResolver,
            android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED
        )

        if (!(gps.matches(".*gps.*".toRegex()) && gps.matches(".*network.*".toRegex()))) {
            AlertDialog.Builder(this)
                .setTitle(R.string.google_map_title_location_service_setting)
                .setMessage(R.string.google_map_msg_location_service_setting)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    startActivity(intent)
                }
                .setNegativeButton(
                    android.R.string.no,
                    DialogInterface.OnClickListener { dialog, which -> return@OnClickListener })
                .create().show()
            return false
        } else {
            return true
        }
    }
}
