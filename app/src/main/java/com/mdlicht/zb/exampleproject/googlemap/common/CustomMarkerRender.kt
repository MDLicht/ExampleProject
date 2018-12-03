package com.mdlicht.zb.exampleproject.googlemap.common

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.mdlicht.zb.exampleproject.googlemap.model.Row

class CustomMarkerRender(context: Context, map: GoogleMap, clusterManager: ClusterManager<Row>) : DefaultClusterRenderer<Row>(context, map, clusterManager) {
    override fun onBeforeClusterItemRendered(item: Row?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Row>?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterRendered(cluster, markerOptions)
    }

    /**
     * Cluster data if size is over 0
     */
    override fun shouldRenderAsCluster(cluster: Cluster<Row>?): Boolean {
        return cluster?.size!! >= 0
    }
}