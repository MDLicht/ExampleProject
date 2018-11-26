package com.mdlicht.zb.exampleproject.googlemap.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
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

class GoogleMapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityGoogleMapBinding
    lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_google_map)
        binding.vm = ViewModelProviders.of(this).get(GoogleMapViewModel::class.java)
        binding.vm!!.clickResult.observe(this, Observer {
            it?.let {
                AlertDialog.Builder(this).setTitle(it.NAME_KOR).setMessage(it.ADD_KOR + '\n' + it.ADD_KOR_ROAD).show().show()
            }
        })
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

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
}
