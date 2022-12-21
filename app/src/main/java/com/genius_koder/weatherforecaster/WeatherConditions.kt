package com.genius_koder.weatherforecaster

import android.nfc.Tag
import android.nfc.tech.TagTechnology
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.weather_conditions.*
import org.json.JSONObject
import java.lang.NullPointerException
import kotlin.math.ceil

//class WeatherConditions : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.weather_conditions)
//
//        // hides title bar
//        try {
//            this.supportActionBar!!.hide()
//        }
//        catch (e: NullPointerException) { }
//
//        var lat = intent.getStringExtra("lat")
//        var long = intent.getStringExtra("long")
//        //Toast.makeText(this,lat,Toast.LENGTH_LONG).show()
//
//        getJsonData(lat,long)
//
//    }
//
//    private fun getJsonData(lat: String?, long: String?) {
//
//          val API_key = "2701d8fb6540b708c1e80ce0611d1dc4"
////        val cnt = "1"
////        val url = "https://api.openweathermap.org/data/2.5/forecast/daily?lat=${lat}&lon=${long}&cnt=${cnt}&appid=${API_key}"
//
////          val API_key = "e03d6ef10c3e4782aae162459222012"
////          val url = "https://www.weatherapi.com/v1/current.json?q=${lat},${long}&key=${API_key}"
////
////        // Instantiate the RequestQueue.
////        val queue = Volley.newRequestQueue(this)
////
////        // Request a string response from the provided URL.
////        var jsonRequest = JsonObjectRequest(
////            Request.Method.GET, url,null,
////            { response ->
////                try {
////                     Log.i("Res",response.toString())
////                    setValues(response)
////                } catch (e : Exception){
////                    e.printStackTrace()
////                }
////            },
////            { error ->
////                // in this case we are simply displaying a toast message.
////                Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT)
////                    .show()
////            }
////        )
////
////        // Add the request to the RequestQueue.
////        queue.add(jsonRequest)
//
//
//        val aqi_url = "https://api.openweathermap.org/data/2.5/air_pollution?lat=${lat}&lon=${long}&mode=xml&appid=${API_key}"
//        // Instantiate the RequestQueue.
//        val aqi_queue = Volley.newRequestQueue(this)
//
//        // Request a string response from the provided URL.
//        var aqi_jsonRequest = JsonObjectRequest(
//            Request.Method.GET, aqi_url,null,
//            { aqi_response ->
//                try {
//                    Log.i("Res",aqi_response.toString())
//                    setAqiValues(aqi_response)
//
//                } catch (e : Exception){
//                    e.printStackTrace()
//                }
//            },
//            { error ->
//                // in this case we are simply displaying a toast message.
//                Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        )
//
//        // Add the request to the RequestQueue.
//        aqi_queue.add(aqi_jsonRequest)
//
//    }
//
//    private fun setAqiValues(aqi_response: JSONObject?) {
//        val condition = arrayOf("Good","Fair","Moderate","Poor","Very Poor")
//        var ind = aqi_response?.getJSONArray("list")?.getJSONObject(0)?.getJSONObject("main")?.getString("aqi")
//        var con = condition[(ind?.toInt() ?: 1) -1]
//        Log.i("INDEX",ind.toString())
//        aqi_value.text = "AQI Level : $con"
//        aqi_des.text = "Air quality index is " + con +  " which is similar to yesterday" + " at about this time."
//    }
//
////    private fun setValues(response: JSONObject) {
////        place_name.text = response.getString("name")                                               // city name
////        humidity_value.text = response.getJSONObject("forecast").getString("humidity.value") // humidity value
////        visibility.text = response.getString("visibility")                                         // visibility value
////        precipitation_value.text = response.getJSONObject("list").getString("rain")          // precipitation value
////
////
////        wind_speed.text = response.getJSONObject("list").getString("speed")                  // wind speed
////        var degree = (response.getJSONObject("list").getString("deg")).toInt()
////        val directions = arrayOf("North", "Northeast", "East", "Southeast", "South", "Southwest", "West", "Northwest" );
////        degree = degree * 8 / 360;
////        degree = ceil(degree.toDouble()).toInt()
////        degree = (degree + 8) % 8
////        wind_direction.text = directions[degree]                                                         // wind direction
////
////
////        var tempr=response.getJSONObject("list").getString("temp.day")
////        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
////        temperature_value.text = "$tempr°C"                                                              // temperature
////        var mintemp=response.getJSONObject("list").getString("temp.min")
////        mintemp=((((mintemp).toFloat()-273.15)).toInt()).toString()
////        min_temp.text = "L : "+mintemp+"°C"                                                              // min temperature
////        var maxtemp=response.getJSONObject("list").getString("temp.max")
////        maxtemp=((Math.ceil((maxtemp).toFloat() - 273.15)).toInt()).toString()
////        max_temp.text = "H : "+maxtemp+"°C"                                                              // max temperature
////
////        sunrise.text = response.getJSONObject("sun").getString("rise")                       // sunrise time
////        sunset.text = response.getJSONObject("sun").getString("set")                         // sunset time
////
////    }
//
//}