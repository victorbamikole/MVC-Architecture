package com.example.task9mvc

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class ConnectivityLiveData (private val connectivityManager: ConnectivityManager):  LiveData<Boolean>(){

    //2
    constructor(application: Application) : this(application.getSystemService(
        Context
            .CONNECTIVITY_SERVICE)
            as ConnectivityManager)

    //Function checks if network is available
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            //4
            postValue(true)
        }

        //Function checks if network is lost
        override fun onLost(network: Network) {
            super.onLost(network)
            //5
            postValue(false)
        }
    }

    //Function checks if network is Active
    override fun onActive() {
        super.onActive()
        val builder = NetworkRequest.Builder()
        //6
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    //Function checks if network is InActive
    override fun onInactive() {
        super.onInactive()
        //7
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }




}