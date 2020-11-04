package com.example.mainactivity.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    String lat;
    String lon;
    String description;
    String temp;
    String feels_like;
    String pressure;
    String humidity;
    String speed;
    String all;
    String name;
    String country;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MainActivity main = (MainActivity) getActivity();
        String weather = main.transfer();

        try {
            JSONObject response = new JSONObject(weather);
            json_parse(response);
        }
        catch (JSONException e)
        {
            // Print out the throwable in case there is an error
            e.printStackTrace();
        }
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView textView = root.findViewById(R.id.textView3);
        TextView textView2 = root.findViewById(R.id.textView4);
        TextView textView3 = root.findViewById(R.id.textView5);
        TextView textView4 =  root.findViewById(R.id.textView7);
        TextView textView5 = root.findViewById(R.id.textView9);
        TextView textView6 = root.findViewById(R.id.textView11);
        TextView textView7 = root.findViewById(R.id.textView13);
        TextView textView8 = root.findViewById(R.id.textView15);
        TextView textView9 = root.findViewById(R.id.textView16);

        textView8.setText("Weather in " + name + ", " + country);
        textView9.setText("Forecast right now: " + temp + "\u00B0F, " + description);
        textView.setText(lon);
        textView2.setText(lat);
        textView3.setText(feels_like + "\u00B0F");
        textView4.setText(pressure);
        textView5.setText(humidity + "%");
        textView6.setText(speed);
        textView7.setText(all);

        return root;
    }

    public void json_parse(JSONObject response)
    {
        // Get all the textviews in order to put values into the textviews

        // Check to see if the JSONObject is not null
        try
        {
            // Parse the JSONObject
            JSONObject coord  = response.getJSONObject("coord");
            lon =  coord.getString("lon");
            lat =  coord.getString("lat");

            JSONArray weather = response.getJSONArray("weather");
            JSONObject index = weather.getJSONObject(0);
            description = index.getString("description");

            JSONObject main = response.getJSONObject("main");
            temp = main.getString("temp");
            Double temp_actual = Double.parseDouble(temp);
            temp = String.valueOf(Math.round(((temp_actual - 273.15) * 9/5 + 32) * 100) / 100);
            feels_like = main.getString("feels_like");
            temp_actual = Double.parseDouble(feels_like);
            feels_like = String.valueOf(Math.round(((temp_actual - 273.15) * 9/5 + 32) * 100) / 100);
            pressure = main.getString("pressure");
            humidity = main.getString("humidity");

            JSONObject wind = response.getJSONObject("wind");
            speed = wind.getString("speed");

            JSONObject clouds = response.getJSONObject("clouds");
            all = clouds.getString("all");

            name = response.getString("name");

            JSONObject sys = response.getJSONObject("sys");
            country = sys.getString("country");

        }
        catch (JSONException e)
        {
            // Print out the throwable in case there is an error
            e.printStackTrace();
        }
    }
}


