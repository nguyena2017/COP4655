package com.example.mainactivity.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;

import org.json.JSONObject;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final EditText editText = root.findViewById(R.id.location);
        Button search = root.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String location = editText.getText().toString();
                MainActivity main = (MainActivity) getActivity();
                main.search(location);
            }
        });
        Button GPS = root.findViewById(R.id.gps);
        GPS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                main.GPS();
            }
        });

        return root;
    }

}