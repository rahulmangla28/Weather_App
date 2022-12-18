package com.genius_koder.weatherforecaster

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.lang.NullPointerException

class LocationPermission : AppCompatActivity() {

    // stores the last known location of the device
    lateinit var mfusedlocation: FusedLocationProviderClient
    private var myRequestCode = 2003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_permission)

        // hides title bar
        try {
            this.supportActionBar!!.hide()
        }
        catch (e: NullPointerException) { }

        // looper is needed due to some bugs in handler(as it is deprecated).
        // looper runs on main thread.
        // runner is executed via lopper.
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,MainActivity::class.java)   // runner content
            startActivity(intent)                                             // runner content
            finish()                                                          // runner content
        },5000)

        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()

    }

    private fun getLastLocation() {
        if(checkPermission()) {
            if(locationEnable()) {
                mfusedlocation.lastLocation.addOnCompleteListener{
                        task->
                    var location : Location?=task.result
                    if(location==null)
                    {
                        NewLocation()
                    }else{
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this,MainActivity::class.java)   // runner content
                            intent.putExtra("lat",location.latitude.toString())
                            intent.putExtra("long",location.longitude.toString())
                            startActivity(intent)
                            finish()
                        },2000)
                    }
                }
            }else{
                Toast.makeText(this,"Please turn on your location", Toast.LENGTH_LONG).show()
            }
        }
        else{
            RequestPermission()
        }
    }

    // request the user for the permissions
    private fun RequestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION),myRequestCode)
    }

    // this runs when sometimes due to some UI changes and google play services failure
    // we have to ask for location again
    @SuppressLint("MissingPermission")
    private fun NewLocation() {
        var locationRequest= LocationRequest()
        locationRequest.priority= LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=0
        locationRequest.fastestInterval=0
        locationRequest.numUpdates=1
        mfusedlocation=LocationServices.getFusedLocationProviderClient(this)
        mfusedlocation.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
    }
    private val locationCallback=object: LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation: Location = p0.lastLocation!!
        }
    }

    // returns whether gps or network is on or not
    private fun locationEnable(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // checks whether the app has access to location
    private fun checkPermission() : Boolean{
        return ( ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                )
    }

    // checks whether the permissions given are sufficient for the app to run
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==myRequestCode)
        {
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                getLastLocation()
            }
        }
    }
}