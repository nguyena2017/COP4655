package com.example.project.ui.favorite;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Company;
import com.example.project.CompanyAdapter;
import com.example.project.Favorite;
import com.example.project.FavoriteAdapter;
import com.example.project.MainActivity;
import com.example.project.R;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter recycleAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<String> company;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        MainActivity main = (MainActivity) getActivity();
        Company test = main.getCompany();
        Favorite object = Favorite.getInstance();
        company = object.favoriteList;
        recyclerView = root.findViewById(R.id.favorite);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recycleAdapter = new FavoriteAdapter(getActivity(), company);
        recyclerView.setAdapter(recycleAdapter);

        return root;
    }
}