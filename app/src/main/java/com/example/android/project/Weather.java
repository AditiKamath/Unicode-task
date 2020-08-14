package com.example.android.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView weatherTemp, weatherCity, description1, feelsLike, weatherDate, tempUnit;
    EditText city_Name;
    Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherDate = findViewById(R.id.weather_date);
        weatherCity = findViewById(R.id.weather_city);
        weatherTemp = findViewById(R.id.weather_temp1);
        description1 = findViewById(R.id.description);
        tempUnit = findViewById(R.id.temp_unit);
        feelsLike = findViewById(R.id.feels_like);
        city_Name = findViewById(R.id.cityName);
        search_button = findViewById(R.id.search);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempUnit.setText("°C");
                find_weather();
            }
        });


    }

    public void find_weather() {

        final String city = city_Name.getText().toString();
        // url to get the weather information
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=5715e8583424d653f6b5cc87e07e2831&units=imperial";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject json_main = response.getJSONObject("main");
                    String temp = String.valueOf(json_main.getDouble("temp"));
                    String feels_like = String.valueOf(json_main.getDouble("feels_like"));
                    JSONArray weather_array = response.getJSONArray("weather");
                    JSONObject first_object = weather_array.getJSONObject(0);
                    String description = first_object.getString("description");
                    weatherCity.setText(city);
                    description1.setText(description);
                    // formatting the date
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy");
                    String format_date = sdf.format(calendar.getTime());
                    weatherDate.setText(format_date);
                    // formatting the temperature
                    Double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32) / 1.8000;
                    centi = Math.round(centi);
                    int i = (int) centi;
                    weatherTemp.setText(String.valueOf(i));
                    Double temp_int1 = Double.parseDouble(feels_like);
                    double centi1 = (temp_int1 - 32) / 1.8000;
                    centi1 = Math.round(centi1);
                    int j = (int) centi1;
                    feelsLike.setText("Feels like : " + String.valueOf(j) + " °C");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Weather.this, "Check City Name!", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }


}
