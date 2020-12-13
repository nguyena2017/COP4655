package com.example.project.ui.search;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.MainActivity;
import com.example.project.ProgramAdapter;
import com.example.project.R;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter recycleAdapter;
    RecyclerView.LayoutManager layoutManager;
    String[] title = {"Wen", "Hel"};
    String[] description = {"This", "Sucks"};
    String TAG = "SearchFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = root.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recycleAdapter = new ProgramAdapter(getActivity(), title, description);
        recyclerView.setAdapter(recycleAdapter);
        final EditText search = root.findViewById(R.id.search);
        Button location_button = root.findViewById(R.id.location_button);

        location_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                //Get the text from the textfield
                String location = search.getText().toString();
                MainActivity main = (MainActivity) getActivity();
                main.search(location);
            }
        });
        return root;
    }
}