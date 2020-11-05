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
import androidx.lifecycle.ViewModelProviders;

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
        String list = main.transfer();
        try {
            JSONObject jsonObj = new JSONObject(list);
            JSONObject test = jsonObj.getJSONObject("main");
            String temp = test.getString("temp");
            String pressure = test.getString("pressure");
            String humidity = test.getString("humidity");
            ArrayList<String> weather = new ArrayList<String>();
            HashMap<String, String> contact = new HashMap<>();

            contact.put("temp", temp);
            contact.put("pressure", pressure);
            //contact.put("humidity",humidity);


            contactList.add(contact);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }

    protected void onPostExecute(Void result) {

    }
}