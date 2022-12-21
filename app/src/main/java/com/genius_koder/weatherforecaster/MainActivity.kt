package com.genius_koder.weatherforecaster

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.weather_conditions.*
import kotlinx.android.synthetic.main.weather_conditions.humidity_value
import kotlinx.android.synthetic.main.weather_conditions.max_temp
import kotlinx.android.synthetic.main.weather_conditions.min_temp
import kotlinx.android.synthetic.main.weather_conditions.place_name
import kotlinx.android.synthetic.main.weather_conditions.temperature_value
import kotlinx.android.synthetic.main.weather_conditions.visibility
import kotlinx.android.synthetic.main.weather_conditions.wind_direction
import kotlinx.android.synthetic.main.weather_conditions.wind_speed
import org.json.JSONObject
import java.lang.Math.ceil
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.activity_main.aqi_value as aqi_value1
import kotlinx.android.synthetic.main.weather_conditions.sunrise as sunrise
import kotlinx.android.synthetic.main.weather_conditions.sunrise_time as sunrise_time1
import kotlinx.android.synthetic.main.weather_conditions.sunset as sunset
import kotlinx.android.synthetic.main.weather_conditions.weather_condition as weather_condition1

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

                                // Fetching data from API using volley library

    private fun getJsonData(lat: String?, long: String?) {

// API key used to fetch the data
        val API_key = "2701d8fb6540b708c1e80ce0611d1dc4"

// Sending a GET Request to fetch details about air quality index
        val aqi_url = "https://api.openweathermap.org/data/2.5/air_pollution?lat=${lat}&lon=${long}&appid=${API_key}"
        // Instantiate the RequestQueue.
        val aqi_queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        var aqi_jsonRequest = JsonObjectRequest(
            Request.Method.GET, aqi_url,null,
            { aqi_response ->
                try {
                    Log.i("Res",aqi_response.toString())
                    setAqiValues(aqi_response)

                } catch (e : Exception){
                    e.printStackTrace()
                }
            },
            { error ->
                // in this case we are simply displaying a toast message.
                Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        // Add the request to the RequestQueue.
        aqi_queue.add(aqi_jsonRequest)


// Sending a GET Request to fetch details about weather conditions
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_key}"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

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


// Extracting AQI value from JSON and displaying it.
    private fun setAqiValues(aqi_response: JSONObject?) {
        val condition = arrayOf("Good","Fair","Moderate","Poor","Very Poor")
        var ind = aqi_response?.getJSONArray("list")?.getJSONObject(0)?.getJSONObject("main")?.getString("aqi")
        var con = condition[(ind?.toInt() ?: 1) -1]
        Log.i("INDEX",ind.toString())
        aqi_value.text = con

        // value of seekbar
    }

// Extracting details about weather conditions from JSON and displaying it.
    private fun setValues(response:JSONObject) {
    // city name
        place_name.text = response.getString("name")
    // weather condition
        weather_condition.text = response.getJSONArray("weather").getJSONObject(0).getString("main")
    // humidity value
        humidity_value.text=response.getJSONObject("main").getString("humidity")+" %"
    // visibility value
        visibility.text = ((response.getString("visibility")).toInt()/1000).toString() + " Km"
    // cloud density
        cloudiness.text = response.getJSONObject("clouds").getString("all") + " %"

    // temperature value
        var tempr=response.getJSONObject("main").getString("temp")
        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
        temperature_value.text="${tempr}°C"
    // minimum temperature value
        var mintemp=response.getJSONObject("main").getString("temp_min")
        mintemp=((((mintemp).toFloat()-273.15)).toInt()).toString()
        min_temp.text="L : " + mintemp+"°C"
    // maximum temperature value
        var maxtemp=response.getJSONObject("main").getString("temp_max")
        maxtemp=((ceil((maxtemp).toFloat()-273.15)).toInt()).toString()
        max_temp.text="H : " + maxtemp+"°C"

    // wind speed
        wind_speed.text = "Speed : " + response.getJSONObject("wind").getString("speed") + " m/s"
    // wind direction
        var degree = (response.getJSONObject("wind").getString("deg")).toInt()
        val directions = arrayOf("North", "Northeast", "East", "Southeast", "South", "Southwest", "West", "Northwest" );
        degree = degree * 8 / 360;
        degree = ceil(degree.toDouble()).toInt()
        degree = (degree + 8) % 8
        wind_direction.text = "Direction : " + directions[degree]

    // sunrise time
        val sun_time = response.getJSONObject("sys").getString("sunrise")
        val HH: Long = TimeUnit.MILLISECONDS.toHours(sun_time.toLong()) %12
        val MM: Long = TimeUnit.MILLISECONDS.toMinutes(sun_time.toLong()) % 60
        val SS: Long = TimeUnit.MILLISECONDS.toSeconds(sun_time.toLong()) % 60
        sunrise_time.text = String.format("%02d:%02d:%02d", HH, MM, SS) + " AM"

    // sunset time
        val set_time = response.getJSONObject("sys").getString("sunset")
        val hh: Long = TimeUnit.MILLISECONDS.toHours(set_time.toLong()) %12
        val mm: Long = TimeUnit.MILLISECONDS.toMinutes(set_time.toLong()) % 60
        val ss: Long = TimeUnit.MILLISECONDS.toSeconds(set_time.toLong()) % 60
        sunset_time.text = String.format("%02d:%02d:%02d", hh, mm, ss) + " PM"

    }
}