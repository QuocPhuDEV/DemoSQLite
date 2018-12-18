package com.example.hoangquocphu.demosqlite.Location;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMaps_Activity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private ProgressDialog myProgress;


    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_);

//        // Tạo Progress Bar.
//        myProgress = new ProgressDialog(this);
//        myProgress.setTitle("Đang tải map ...");
//        myProgress.setMessage("Vui lòng đợi...");
//        myProgress.setCancelable(true);
//        // Hiển thị Progress Bar.
//        myProgress.show();


        // Ánh xạ đối tượng
        SupportMapFragment mapFragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(10.902487, 106.743881);
        myMap.addMarker(new MarkerOptions().position(sydney).title("SVN Company"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
