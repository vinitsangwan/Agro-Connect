package com.agroconnect.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

object LocationHelper {
    private const val TAG = "LocationHelper"

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): Location? {
        return try {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            
            // Try to get last known location first (faster)
            val lastLocation = fusedLocationClient.lastLocation.await()
            if (lastLocation != null) {
                Log.d(TAG, "Got last known location: ${lastLocation.latitude}, ${lastLocation.longitude}")
                return lastLocation
            }

            // If not available, request fresh location
            val result = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).await()
            
            Log.d(TAG, "Got fresh location: ${result?.latitude}, ${result?.longitude}")
            result
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get location: ${e.message}")
            null
        }
    }
}
