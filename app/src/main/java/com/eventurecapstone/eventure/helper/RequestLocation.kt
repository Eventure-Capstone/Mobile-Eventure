package com.eventurecapstone.eventure.helper

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.eventurecapstone.eventure.data.pref.UserPreference.Coordinate
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CompletableDeferred

object RequestLocation {

    private lateinit var activity: FragmentActivity
    private lateinit var result: CompletableDeferred<Coordinate?>
    private lateinit var launcherLocation: ActivityResultLauncher<Array<String>>

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun initLauncherLocation(activity: FragmentActivity){
        if (!this::activity.isInitialized || this.activity != activity){
            this.activity = activity
        }

        launcherLocation = this.activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> getMyLocation()
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> getMyLocation()
                else -> result.complete(null)
            }
        }
    }

    private fun getMyLocation(){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        val fineLoc = checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLoc = checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (fineLoc && coarseLoc) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val coordinate = if (location != null) Coordinate(location.latitude, location.longitude) else null
                result.complete(coordinate)
            }
        } else {
            launcherLocation.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    suspend fun getLocation(activity: FragmentActivity): Coordinate? {
        if (!this::activity.isInitialized || this.activity != activity){
            initLauncherLocation(activity)
        }
        result = CompletableDeferred()

        getMyLocation()

        return result.await()
    }
}