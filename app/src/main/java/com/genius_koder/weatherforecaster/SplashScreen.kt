package com.genius_koder.weatherforecaster

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)  // Displaying Splash Screen

        // looper is needed due to some bugs in handler(as it is deprecated).
        // looper runs on main thread.
        // runner is executed via lopper.
        Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this,LocationPermission::class.java)   // runner content
                startActivity(intent)                                             // runner content
                finish()                                                          // runner content
        },4000)

    }

}