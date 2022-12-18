package com.genius_koder.weatherforecaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.weather_conditions.*
import org.json.JSONObject
import java.lang.Math.ceil
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hides title bar
        try {
            this.supportActionBar!!.hide()
        }
        catch (e: NullPointerException) { }

        var lat = intent.getStringExtra("lat")
        var long = intent.getStringExtra("long")

        getJsonData(lat,long)
    }

    private fun getJsonData(lat: String?, long: String?) {

        val API_key = "2701d8fb6540b708c1e80ce0611d1dc4"
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/3.0/onecall?lat=${lat}&lon=${long}&exclude={part}&appid={API_key}"

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                setValues(response)
            },
            { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun setValues(response:JSONObject) {
        place_name.text = response.getString("name")
        uv_index.text = response.getString("current.uvi")
        sunrise.text = response.getString("daily.sunrise")
        sunset.text = response.getString("daily.sunset")

        var tempr=response.getString("current.temp")
        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
        temperature_value.text="${tempr}°C"
        var mintemp=response.getString("daily.min.temp")
        mintemp=((((mintemp).toFloat()-273.15)).toInt()).toString()
        min_temp.text=mintemp+"°C"
        var maxtemp=response.getJSONObject("main").getString("temp_max")
        maxtemp=((ceil((maxtemp).toFloat()-273.15)).toInt()).toString()
        max_temp.text=maxtemp+"°C"

        


    }
}