package com.example.temparaturechecker;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.util.Calendar;

public class roomtemparature extends AppCompatActivity{
    Button progress1;
    int p;
    ProgressBar cp;
    double kl,fer;
    TextView time,temp1;
    private SensorManager sensorManager;
    private Sensor pressure;
    private Sensor tempSensor;
    private Boolean isTemperatureSensorAvailable;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df = new DecimalFormat("#.##");
    TextView tvResult1,tempre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomtemparature);
      // tvResult1=findViewById(R.id.tvResult);
        getWeatherDetails();
        progress1=findViewById(R.id.progress);
        cp=findViewById(R.id.progressBar);
        time=findViewById(R.id.time);
        temp1=findViewById(R.id.temp);
        Calendar c = Calendar.getInstance();
        String[]dayName={" ","Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(c.getTime());
        String sDate = c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE)+"\n\n"+month_name+" "+c.get(Calendar.DAY_OF_MONTH);


        SpannableString ss1=  new SpannableString(sDate);
        ss1.setSpan(new RelativeSizeSpan(2.5f), 0,5, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.rgb(244,81,30)), 0, 5, 0);// set color

        time.setText(ss1);


        progress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              temp1.setText(String.valueOf(fer));
              cp.setProgress((int)fer);
            }


        });


    }



    public void getWeatherDetails() {
        String tempUrl = "";
        //Log.d("data2","adsf");
        String city = "attock";

        String country = "Pakistan";
        if (city.equals("")) {
            tvResult1.setText("City field can not be empty!");
        } else {
            //Toast.makeText(roomtemparature.this,"thisdfasda",Toast.LENGTH_SHORT).show();
            //Log.d("data","adsf");
            if (!country.equals("")) {
                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
            } else {
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {

                public void onResponse(String response) {
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        kl=temp;
                        cp.setProgress((int)kl);
                        fer=kl+32.0;
                        temp1.setText(String.valueOf(temp));
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");
                        //tvResult1.setTextColor(Color.rgb(68, 134, 199));

                        //tvResult1.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

}