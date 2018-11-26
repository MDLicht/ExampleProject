package com.mdlicht.zb.exampleproject.googlemap.viewmodel

import android.app.AlertDialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.util.Log
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.mdlicht.zb.exampleproject.googlemap.api.RetrofitUtil
import com.mdlicht.zb.exampleproject.googlemap.model.Response
import com.mdlicht.zb.exampleproject.googlemap.model.Row
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GoogleMapViewModel : ViewModel(), ClusterManager.OnClusterItemClickListener<Row>, ClusterManager.OnClusterClickListener<Row> {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var map: GoogleMap? = null
    lateinit var clusterManager: ClusterManager<Row>
    private val stationData: ObservableList<Row> = ObservableArrayList()
    val clickResult = MutableLiveData<Row>()

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
                    Observable.fromIterable(it.SebcBicycleRetalKor?.row)
                }
                .filter {
                    it.LATITUDE?.isNotEmpty() ?: false
                }
                .filter {
                    it.LONGITUDE?.isNotEmpty() ?: false
                }
                .subscribe({
                    it?.let {
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

    override fun onClusterItemClick(p0: Row?): Boolean {
        clickResult.postValue(p0)
        return true
    }

    override fun onClusterClick(p0: Cluster<Row>?): Boolean {
        if(p0?.size == 1) {
            clickResult.postValue(p0.items.first())
        } else {
            map?.let {
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(p0?.position, it.cameraPosition.zoom + 1))
            }
        }
        return true
    }
}