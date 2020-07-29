package com.example.android.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Weather extends AppCompatActivity {
    TextView weatherdate, weathertemp1, weather_temp2, weathercity, description1,feelslike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherdate = findViewById(R.id.weather_date);
        weathercity = findViewById(R.id.weather_city);
        weathertemp1 = findViewById(R.id.weather_temp1);
        weather_temp2 = findViewById(R.id.weather_temp2);
        description1 = findViewById(R.id.description);
        feelslike = findViewById(R.id.feels_like);
        find_weather();

    }

    public void find_weather() {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Mumbai,India&appid=5715e8583424d653f6b5cc87e07e2831&units=imperial";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_main = response.getJSONObject("main");
                    String temp = String.valueOf(json_main.getDouble("temp"));
                    String feels_like = String.valueOf(json_main.getDouble("feels_like"));
                    JSONArray weather_array = response.getJSONArray("weather");
                    JSONObject first_object = weather_array.getJSONObject(0);
                    String description = first_object.getString("description");
                    String city = response.getString("name");
                //    weathertemp1.setText(temp);
                    weathercity.setText(city);
                    description1.setText(description);
                    feelslike.setText(feels_like);
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy");
                    String format_date = sdf.format(calendar.getTime());
                    weatherdate.setText(format_date);
                    Double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int-32)/1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    weathertemp1.setText(String.valueOf(i));

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("no info ");
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }


}
