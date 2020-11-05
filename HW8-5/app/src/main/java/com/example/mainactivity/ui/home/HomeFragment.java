package com.example.mainactivity.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;



public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final EditText editText = root.findViewById(R.id.location);
        Button search = root.findViewById(R.id.search);
        //Add onclick event to the GPS button
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get the text from the textfield
                final String location = editText.getText().toString();
                MainActivity main = (MainActivity) getActivity();
                main.search(location);
            }
        });
        Button GPS = root.findViewById(R.id.gps);
        //Add onclick event to the button
        GPS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                main.GPS();
            }
        });

        ImageView speak = root.findViewById(R.id.speak);
        //Add onclick event to the speak button
        speak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                main.speechToText();
            }
        });
        return root;
    }

}