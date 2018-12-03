package com.mdlicht.zb.exampleproject.googlemap.viewmodel

import android.Manifest
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.content.pm.PackageManager
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.mdlicht.zb.exampleproject.googlemap.api.RetrofitUtil
import com.mdlicht.zb.exampleproject.googlemap.model.Row
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GoogleMapViewModel : ViewModel(), ClusterManager.OnClusterItemClickListener<Row>,
    ClusterManager.OnClusterClickListener<Row>, LocationListener {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var map: GoogleMap? = null
    lateinit var clusterManager: ClusterManager<Row>
    private val stationData: ObservableList<Row> = ObservableArrayList()
    val clickResult = MutableLiveData<Row>()
    val gpsClick = MutableLiveData<Array<String>>()

    /**
     * Load Station info list when view model created
     */
    init {
        loadStationList()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun loadStationList() {
        compositeDisposable.add(
            RetrofitUtil.create()
                .getAllStationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    Observable.fromIterable(it.SebcBicycleRetalKor?.row) // Convert result into station info's list
                }
                .filter {
                    it.LATITUDE?.isNotEmpty() ?: false // filter item Latitude is empty
                }
                .filter {
                    it.LONGITUDE?.isNotEmpty() ?: false // filter item Longitude is empty
                }
                .subscribe({
                    it?.let {
                        // Add station info to dataSet and draw marker
                        stationData.add(it)
                        addStationMarker(it)
                    }
                }, {
                    Log.e("Error", "Load Failed : " + it.message)
                })
        )
    }

    private fun addStationMarker(row: Row) {
        clusterManager.addItem(row)
    }

    /**
     * Post station info to observer
     */
    override fun onClusterItemClick(p0: Row?): Boolean {
        clickResult.postValue(p0)
        return true
    }

    /**
     * Post stations info to observer or zoom in 1 level
     */
    override fun onClusterClick(p0: Cluster<Row>?): Boolean {
        if (p0?.size == 1) {
            clickResult.postValue(p0.items.first())
        } else {
            map?.let {
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(p0?.position, it.cameraPosition.zoom + 1))
            }
        }
        return true
    }

    /**
     * Post permission list to observer
     */
    fun onGpsClick(view: View) {
        gpsClick.postValue(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
    }

    /**
     * Move camera
     */
    override fun onLocationChanged(location: Location?) {
        map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location?.latitude!!, location?.longitude!!)))
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // DO NOTHING
    }

    override fun onProviderEnabled(provider: String?) {
        // DO NOTHING
    }

    override fun onProviderDisabled(provider: String?) {
        // DO NOTHING
    }
}