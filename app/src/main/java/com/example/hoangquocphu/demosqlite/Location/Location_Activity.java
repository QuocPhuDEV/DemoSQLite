package com.example.hoangquocphu.demosqlite.Location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hoangquocphu.demosqlite.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Location_Activity extends Location {
    protected GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_);
        showMaps();
    }

    private void showMaps() {
        showDialogLoading();

        // Ánh xạ đối tượng
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMaps);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        dismissDialog();
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                    }
                });

                LatLng SVNLocation = new LatLng(10.902520, 106.743914);
                mGoogleMap.addMarker(new MarkerOptions().position(SVNLocation).title("SVN Company"));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SVNLocation, 18));
            }
        });
    }
}
