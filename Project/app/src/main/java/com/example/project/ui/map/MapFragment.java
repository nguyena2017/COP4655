package com.example.project.ui.map;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.Company;
import com.example.project.Favorite;
import com.example.project.MainActivity;
import com.example.project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;

public class MapFragment extends Fragment {

    private GoogleMap googleMap;
    private MapView mMapView;
    private double latitude;
    private double longitude;
    public int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        MainActivity main = (MainActivity) getActivity();
        Company company = main.getCompany();

        TextView name = root.findViewById(R.id.mapName);
        TextView phone = root.findViewById(R.id.mapPhone);
        TextView address = root.findViewById(R.id.mapAddress);
        TextView city = root.findViewById(R.id.mapCity);
        ImageButton next = root.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refresh();
            }
        });
        if(company.name.size() == 0)
        {
            return root;
        }
        name.setText(company.name.get(counter));
        phone.setText(company.phone.get(counter));
        address.setText(company.address.get(counter));
        city.setText(company.city.get(counter));

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create the Google Maps with the overlay
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                MainActivity main = (MainActivity) getActivity();
                googleMap = mMap;
                Company company = main.getCompany();

                latitude = company.latitude.get(counter);
                longitude = company.longitude.get(counter);

                // For dropping a marker at a point on the Map
                LatLng location = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(location).title("Your location"));

                // For zooming
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

            }
        });
        return root;
    }

    public void refresh()
    {
        counter++;
        if(counter == 10)
        {
            counter = 0;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}