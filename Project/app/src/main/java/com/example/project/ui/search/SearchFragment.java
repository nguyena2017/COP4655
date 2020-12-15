package com.example.project.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Company;
import com.example.project.MainActivity;
import com.example.project.CompanyAdapter;
import com.example.project.R;
import com.example.project.VolleyCallBack;

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
    ArrayList<String> rating = new ArrayList<String>();
    ArrayList<String> image_url = new ArrayList<String>();

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        // Get the recycler View from the id
        recyclerView = root.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Get the EditText and ImageButton from the id
        final EditText search = root.findViewById(R.id.search);
        final EditText term = root.findViewById(R.id.term);
        ImageButton location_button = root.findViewById(R.id.location_button);
        ImageButton gps_button = root.findViewById(R.id.gps_button);


        MainActivity main = (MainActivity) getActivity();
        Company company = main.getCompany();
        name = company.name;
        address = company.address;
        city = company.city;
        phone = company.phone;
        is_closed = company.is_closed;
        rating = company.rating;
        image_url = company.image_url;

        recycleAdapter = new CompanyAdapter(getActivity(), name, address, phone, is_closed, rating, city, image_url);
        recyclerView.setAdapter(recycleAdapter);
        location_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Get the text from the textfield
                String location = search.getText().toString();
                String category = term.getText().toString();
                final MainActivity main = (MainActivity) getActivity();
                main.search(location, category, new VolleyCallBack()
                {
                    @Override
                    public void onSuccess()
                    {
                        Company company = main.getCompany();
                        refresh(company);
                    }
                });
            }
        });

        gps_button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final MainActivity main = (MainActivity) getActivity();
                String category = term.getText().toString();
                main.GPS(category, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Company company = main.getCompany();
                        refresh(company);
                    }
                });

            }
        });
        return root;
    }

    public void refresh(Company company)
    {
        name = company.name;
        address = company.address;
        city = company.city;
        phone = company.phone;
        is_closed = company.is_closed;
        rating = company.rating;
        image_url = company.image_url;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}