package com.example.mainactivity.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.ui.dashboard.DashboardFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationsFragment extends Fragment {

    private GoogleMap googleMap;
    private MapView mMapView;
    private double latitude;
    private double longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                MainActivity main = (MainActivity) getActivity();
                String weather = main.transfer();
                if(weather == null)
                {
                    return;
                }
                try {
                    JSONObject response = new JSONObject(weather);
                    JSONObject coord  = response.getJSONObject("coord");
                    String lon =  coord.getString("lon");
                    String lat =  coord.getString("lat");
                    latitude = Double.parseDouble(lat);
                    longitude = Double.parseDouble(lon);
                } catch (JSONException e) {
                    // Print out the throwable in case there is an error
                    e.printStackTrace();
                }

                googleMap = mMap;

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
}