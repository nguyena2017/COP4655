package com.example.mainactivity.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class historyFragment extends Fragment {

    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<String> list_item = new ArrayList<String>();
    ArrayList<JSONObject> test;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        lv = (ListView) root.findViewById(R.id.list);
        contactList = new ArrayList<>();

        MainActivity main = (MainActivity) getActivity();

        for(int i = 0; i < 5; i++)
        {
            String date = "";
            String temp ="";
            String pressure ="";
            String humidity="";
            String speed = "";
            String name = main.getHistory(i);
            Log.d("TAG", "hello" + name);
            try {
                JSONObject response = new JSONObject(name);

                JSONObject current = response.getJSONObject("current");
                date = current.getString("dt");
                long date_actual = Long.parseLong(date);
                date = new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date (date_actual*1000));
                temp = current.getString("temp");
                Double temp_actual = Double.parseDouble(temp);
                temp = String.valueOf(Math.round(((temp_actual - 273.15) * 9/5 + 32) * 100) / 100);
                pressure = current.getString("pressure");
                humidity = current.getString("humidity");

                Log.d("Tag", temp);

                speed = current.getString("wind_speed");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            HashMap<String, String> contact = new HashMap<>();
            contact.put("date", date);
            contact.put("temp", "Temperature: " + temp);
            contact.put("pressure", "Pressure: " + pressure);
            contact.put("humidity", "Humidity: " + humidity);
            contact.put("wind", "Wind Speed: " + speed);

            contactList.add(contact);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity(), contactList,
                R.layout.list_items, new String[]{ "date", "temp","pressure", "humidity", "wind"},
                new int[]{R.id.date, R.id.temp, R.id.pressure, R.id.humidity, R.id.wind});
        lv.setAdapter(adapter);
        return root;
    }



}