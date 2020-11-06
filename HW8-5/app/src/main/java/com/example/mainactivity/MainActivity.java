package com.example.mainactivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.JsonReader;
import android.util.Log;
import android.content.pm.PackageManager;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainactivity.ui.weather.WeatherFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    String weather;
    String weather1;
    ArrayList<String> history = new ArrayList<String>();
    // Used for location permission
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    // Used for location permission
    private static final int REQUEST_CODE_PERMISSION = 2;
    // GPSTracker object
    GPSTracker gps;

    private final int REQ_CODE = 100;

    TextToSpeech t1;

    double lon;
    double lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    //Using the search feature
    public void search(String location) {
        String url = "";

        // Check to see if nothing been inputted
        if (location.equals("")) {
            // Do nothing if user inputs nothing
            return;
        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Checks to if city has been inputted or a zip code by using parseDouble
        try {

            double number = Double.parseDouble(location);
            // Using Zip Code for OpenWeather API
            url = "https://api.openweathermap.org/data/2.5/weather?zip=" + location + "&appid=323a24aad0160f9b25ba1116381f995b";

        }
        // Catches if location can not be turned into double.
        catch (NumberFormatException ex) {
            // Using city for OpenWeather API
            url = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=323a24aad0160f9b25ba1116381f995b";
        }

        // Request a JSONObject response from the provided URL.
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        weather = response.toString();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Display error
                Toast.makeText(getApplicationContext(), "That didn't work", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(JSONRequest);
        history();
    }

    //Return the JSONObject String
    public String transfer() {

        try {
            JSONObject response = new JSONObject(weather);
            JSONObject coord = response.getJSONObject("coord");
            lon = Double.parseDouble(coord.getString("lon"));
            lat = Double.parseDouble(coord.getString("lat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weather;
    }

    public String getHistory(int i) {
        return history.get(i);
    }


    //Using GPS feature
    public void GPS() {
        // Checks to see if user gives permission to use their location
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Initialize the GPS
        gps = new GPSTracker(MainActivity.this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Using GPS coordinates for OpenWeather API
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) + "&appid=323a24aad0160f9b25ba1116381f995b";
        Log.d("TAG", url);
        // Request a JSONObject response from the provided URL.
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        weather = response.toString();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Display error
                Toast.makeText(getApplicationContext(), "That didn't work", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(JSONRequest);
        history();
    }

    public void speechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        try {
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry your device not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Used for the speech to text
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    search(result.get(0).toString());
                }
                break;
            }
        }
    }

    // Used for the text to speech
    public void textToSpeech(String toSpeak) {
        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    //For weather history
    public void history() {

        LocalDate current = LocalDate.now();
        for (int i = 0; i < 5; i++) {

            long past_date = current.atStartOfDay(ZoneOffset.UTC).toEpochSecond() - 86400 * i;

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);

            WeatherFragment weather = new WeatherFragment();

            //calls to 5 day forecast
            String url = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat + "&lon=" + lon + "&dt=" + past_date + "&appid=323a24aad0160f9b25ba1116381f995b";

            JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            history.add(response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //textView.setText("That didn't work!");
                        }
                    });
            queue.add(JSONRequest);
        }
    }
}

