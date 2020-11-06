package com.example.mainactivity.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class historyFragment extends Fragment {

    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        lv = (ListView) root.findViewById(R.id.list);
        contactList = new ArrayList<>();

        MainActivity main = (MainActivity) getActivity();

        // Putting the weather info into a Hashmap
        for(int i = 0; i < 5; i++)
        {
            String date = "";
            String temp ="";
            String pressure ="";
            String humidity="";
            String speed = "";
            String day = "";

            //Check to see if history variable has been initialized
            try {
                 day = main.getHistory(i);
            }
            //If the history variable has not been initialized, show blank screen
            catch (IndexOutOfBoundsException ex)
            {
                return root;
            }

            try {
                // Parsing the JSON for each day
                JSONObject response = new JSONObject(day);

                JSONObject current = response.getJSONObject("current");
                date = current.getString("dt");
                long date_actual = Long.parseLong(date);
                date = new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date (date_actual*1000));
                temp = current.getString("temp");
                Double temp_actual = Double.parseDouble(temp);
                temp = String.valueOf(Math.round(((temp_actual - 273.15) * 9/5 + 32) * 100) / 100);
                pressure = current.getString("pressure");
                humidity = current.getString("humidity");
                speed = current.getString("wind_speed");


                //Catch any JSON exception
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Putting into the hashmap
            HashMap<String, String> weather = new HashMap<>();
            weather.put("date", date);
            weather.put("temp", "Temperature: " + temp + "\u00B0F");
            weather.put("pressure", "Pressure: " + pressure);
            weather.put("humidity", "Humidity: " + humidity + "%");
            weather.put("wind", "Wind Speed: " + speed);

            contactList.add(weather);
        }
        //Putting the weather info from the days into a list view
        ListAdapter adapter = new SimpleAdapter(getActivity(), contactList,
                R.layout.list_items, new String[]{ "date", "temp","pressure", "humidity", "wind"},
                new int[]{R.id.date, R.id.temp, R.id.pressure, R.id.humidity, R.id.wind});
        lv.setAdapter(adapter);
        return root;
    }
}