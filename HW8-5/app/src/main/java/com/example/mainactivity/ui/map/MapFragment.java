package com.example.mainactivity.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

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
                String weather = main.transfer();
                if(weather == null)
                {
                    return;
                }
                // Get the coordinate for the Google Map
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
                // Weather overlay
                TileProvider tileProvider = new UrlTileProvider(256, 256) {
                    @Override
                public URL getTileUrl(int x, int y, int zoom) {

                    String s = String.format("https://tile.openweathermap.org/map/temp_new/%d/%d/%d.png?appid=323a24aad0160f9b25ba1116381f995b", zoom, x, y);
                    if (!checkTileExists(x, y, zoom)) {
                        return null;
                    }            try {
                        return new URL(s);
                    } catch (MalformedURLException e) {
                        throw new AssertionError(e);
                    }
                }
                    private boolean checkTileExists(int x, int y, int zoom)
                    {
                        int minZoom = 12;
                        int maxZoom = 16;            return (zoom >= minZoom && zoom <= maxZoom);
                    }
                };

                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng location = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(location).title("Your location"));

                // For zooming
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                TileOverlay tileOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));

            }
        });
        return root;
    }
}