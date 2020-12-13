package com.example.project.ui.search;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Company;
import com.example.project.MainActivity;
import com.example.project.ProgramAdapter;
import com.example.project.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter recycleAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> city = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<String>();
    ArrayList<String> is_closed = new ArrayList<String>();
    ArrayList<String> distance = new ArrayList<String>();
    ArrayList<String> rating = new ArrayList<String>();
    String TAG = "SearchFragment";

    LayoutInflater inflater;
    ViewGroup container;
    Bundle savedInstanceState;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;

        recyclerView = root.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final EditText search = root.findViewById(R.id.search);
        Button location_button = root.findViewById(R.id.location_button);
        Button gps_button = root.findViewById(R.id.gps_button);

        MainActivity main = (MainActivity) getActivity();
        Company company = main.getCompany();
        name = company.name;
        address = company.address;
        city = company.city;
        phone = company.phone;
        is_closed = company.is_closed;
        distance = company.distance;
        rating = company.rating;
        Log.d(TAG, "message");

        recycleAdapter = new ProgramAdapter(getActivity(), name, address, phone, is_closed, distance, rating, city);
        recyclerView.setAdapter(recycleAdapter);
        location_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Get the text from the textfield
                String location = search.getText().toString();
                MainActivity main = (MainActivity) getActivity();
                main.search(location);
                Company company = main.getCompany();
            }
        });

        gps_button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                MainActivity main = (MainActivity) getActivity();
                main.GPS();
                Company company = main.getCompany();
                recycleAdapter = new ProgramAdapter(getActivity(), name, address, phone, is_closed, distance, rating, city);
                recyclerView.setAdapter(recycleAdapter);
            }
        });

        return root;
    }
}