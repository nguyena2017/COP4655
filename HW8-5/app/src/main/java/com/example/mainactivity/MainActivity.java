package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.weather.MESSAGE";
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
    }

    // Does the function when the search button is clicked
    public void search(View view)
    {
        //Get the editText
        EditText editText = (EditText) findViewById(R.id.location);

        // Get the value from the EditText that the user inputted
        String location = editText.getText().toString();
        String url = "";

        // Check to see if nothing been inputted
        if(location.equals(""))
        {
            // Do nothing if user inputs nothing
            return;
        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Checks to if city has been inputted or a zip code by using parseDouble
        try
        {

            double number = Double.parseDouble(location);
            // Using Zip Code for OpenWeather API
            url = "https://api.openweathermap.org/data/2.5/weather?zip=" + location + "&appid=323a24aad0160f9b25ba1116381f995b";

        }
        // Catches if location can not be turned into double.
        catch (NumberFormatException ex)
        {
            // Using city for OpenWeather API
            url = "https://api.openweathermap.org/data/2.5/weather?q=" + location +"&appid=323a24aad0160f9b25ba1116381f995b";
        }

        // Request a JSONObject response from the provided URL.
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        // Transfer the string information into MapActivity
                        intent.putExtra(EXTRA_MESSAGE, response.toString());
                        // Start the MapActivity
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Display error
                Toast.makeText(getApplicationContext(),"That didn't work", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(JSONRequest);
    }
}