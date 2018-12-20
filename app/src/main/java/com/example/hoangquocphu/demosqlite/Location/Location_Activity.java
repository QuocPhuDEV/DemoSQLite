package com.example.hoangquocphu.demosqlite.Location;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.MainActivity;
import com.example.hoangquocphu.demosqlite.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Location_Activity extends AppCompatActivity implements LocationListener {

    // Khai báo đối tượng Map
    protected GoogleMap myMap;
    private ProgressDialog myProgress;

    // Khai báo các control
    private TextView tvLocation, tvGPS;

    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;

    // Khai báo biến nhận giá trị kinh và vĩ độ
    public double Longitude = 0;
    public double Latitude = 0;

    // Kiểm tra tính khả dụng của
    public boolean isGPSEnabled = false;
    public boolean isNETWORK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_);

        addObject();
        formLoad();

        // Tạo Progress Bar.
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Đang load vị trí ...");
        myProgress.setMessage("Vui lòng chờ...");
        myProgress.setCancelable(true);
        // Hiển thị Progress Bar.
        myProgress.show();

        SupportMapFragment mapFragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMaps);

        // Xử lý sự kiện lấy vị trí GPS
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                onMyMapReady(googleMap);
            }
        });

    }

    // Ánh xạ đối tượng
    public void addObject() {
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvGPS = (TextView) findViewById(R.id.tvGPS);
    }

    // Form load
    public void formLoad() {
        try {
            Intent intent = this.getIntent();
            Longitude = intent.getDoubleExtra("Longitude", 0);
            Latitude = intent.getDoubleExtra("Latitude", 0);
        } catch (Exception e) {

        }
    }

    private void onMyMapReady(GoogleMap googleMap) {
        try {
            // Gán google maps cho fragment
            myMap = googleMap;
            // Sự kiện click lấy vị trí
            myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                @Override
                public void onMapLoaded() {
                    // Map loaded. Load xong thì đóng progress bar
                    myProgress.dismiss();

                    // Hỏi quyền truy cập từ người dùng
                    askPermissionsAndShowMyLocation();
                }
            });

            // Set kiểu hiển thị của Map
            myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            // Set kiểu Zoom map
            myMap.getUiSettings().setZoomControlsEnabled(true);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            myMap.setMyLocationEnabled(true);
        } catch (Exception e) {

        }
    }

    private void askPermissionsAndShowMyLocation() {

        try {
            // Nếu API> = 23, hiển thị thông báo hỏi người dùng về quyền truy cập
            if (Build.VERSION.SDK_INT >= 23) {
                int accessCoarsePermission
                        = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                int accessFinePermission
                        = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


                if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                        || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
                    // Hỏi quyền từ người dùng
                    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION};
                    // Hiển thị dialog xác nhận
                    ActivityCompat.requestPermissions(this, permissions,
                            REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                    return;
                }
            }

            // Hiển thị vị trí hiện tại lên Map
            this.showMyLocation();
        } catch (Exception e) {

        }
    }

    // Sau khi xác nhận quyền truy cập từ người dùng
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        try {
            switch (requestCode) {
                case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {

                    // Gán quyền khi người dùng xác nhận
                    if (grantResults.length > 1
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "Đã gán quyền truy cập vị trí", Toast.LENGTH_LONG).show();

                        // Hiển thị vị trí lên Map
                        this.showMyLocation();
                    }
                    // Thông báo khi không được cấp quyền vị trí
                    else {
                        Toast.makeText(this, "Chưa cấp quyền truy cập vị trí", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        } catch (Exception e) {

        }
    }

    // Tìm vị trí cụ thể thông qua GPS hoặc NETWORK
    private String getEnabledLocationProvider() {

        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Khai báo đối tượng
            Criteria criteria = new Criteria();

            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setCostAllowed(false);

            // Kiểm tra chức năng khả dụng ( GPS hay NETWORK)
            String bestProvider = locationManager.getBestProvider(criteria, true);

            boolean enabled = locationManager.isProviderEnabled(bestProvider);
            //boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!enabled) {
                Toast.makeText(this, "GPS hoặc NETWORK không khả dụng", Toast.LENGTH_LONG).show();
                return null;
            }
            return bestProvider;
        } catch (Exception e) {
            return null;
        }
    }

    // Khi được cấp quyền truy cập vị trí, lấy vị trí hiện tại
    private void showMyLocation() {

        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


            //String locationProvider = this.getEnabledLocationProvider();

            // Lấy vị trí trục tiếp theo network
            String locationProvider = LocationManager.NETWORK_PROVIDER;

            // Kiểm tra network hay GPS khả dụng
            //String locationProvider = checkGPSorNETWORK();


            if (locationProvider == null) {
                return;
            }

            // Millisecond
            final long MIN_TIME_BW_UPDATES = 1000;
            // Met
            final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

            Location myLocation = null;
            try {
                // Thiết lập thông tin cho location manager
                locationManager.requestLocationUpdates(
                        locationProvider,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                // Lấy ra vị trí.
                myLocation = locationManager
                        .getLastKnownLocation(locationProvider);
            }
            // Nếu API >=23, kiểm tra SecurityException
            catch (SecurityException e) {
                Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }

            if (myLocation != null) {

                // Gán vị trí tìm được lên map
                //LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                LatLng latLng = null;
                if (Longitude != 0 || Latitude != 0) {
                    latLng = new LatLng(Latitude, Longitude);
                    tvGPS.setText("Kinh độ: " + Latitude + "           Vĩ độ: " + Longitude);
                    getAddress(Latitude, Longitude);
                } else {
                    latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    tvGPS.setText("Kinh độ: " + myLocation.getLongitude() + "           Vĩ độ: " + myLocation.getLatitude());
                    getAddress(myLocation.getLatitude(), myLocation.getLongitude());
                }


                // Thiết lập chế độ xem ( vừa di chuyển vừa zoom màn hình)
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)             // Thiết lập trung tâm bản đồ theo vị trí người dùng hiện tại
                        .zoom(15)                   // Thiết lập mức độ zoom
                        .bearing(90)                // Đặt hướng camera về hướng đông
                        .tilt(40)                   // Thiết lập độ nghiêng của camera ( đơn vị độ)
                        .build();                   // Tạo mới camera từ những thiết lập trên
                myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                // Tạo 1 vị trí trên Map ( vị trí của người dùng theo GPS)
                MarkerOptions option = new MarkerOptions();
                option.title("Vị trí của tôi");
                option.snippet("");
                option.position(latLng);
                Marker currentMarker = myMap.addMarker(option);
                currentMarker.showInfoWindow();
            } else {
                //Toast.makeText(this, "Không thể tim thấy vị trí", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Xác thực vị trí không thành công, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }

    }

    // Lấy địa chỉ cụ thể từ kinh độ và vĩ độ
    public void getAddress(double latitude, double longtitude) {
        Geocoder geocoder;
        List<Address> address;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            address = geocoder.getFromLocation(latitude, longtitude, 1);
            String _address = address.get(0).getAddressLine(0);
            String _city = address.get(0).getLocality();
            String _area = address.get(0).getAdminArea();
            String _country = address.get(0).getCountryName();
            String _knowName = address.get(0).getFeatureName();
            String _number = address.get(0).getLocale().getDisplayCountry();

            tvLocation.setText("Địa chỉ: " + _address);
        } catch (IOException e) {

        }

    }

    // Kiểm tra tình trạng khả dụng của GPS và NETWORK
    // NÊN LƯU Ý PHẦN NÀY, NẾU LẤY THÔNG QUA NETWORK THÌ SẼ CHÍNH XÁC HƠN
    // NHƯNG NẾU KHÔNG CÓ MẠNG THÌ BUỘC PHẢI LẤY QUA GPS
    
    public String checkGPSorNETWORK() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String locationProvider = "";
        try {


            // Kiểm tra tình trạng GPS
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Kiểm tra tình trạng NETWORK
            isNETWORK = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled != false && isNETWORK != false) {
                Toast.makeText(this, "Vui lòng kiểm tra tình trạng GPS hoặc NETWORK.", Toast.LENGTH_SHORT).show();
            } else {
                if (isGPSEnabled) {
                    locationProvider = LocationManager.GPS_PROVIDER;
                }
                if (isNETWORK) {
                    locationProvider = LocationManager.NETWORK_PROVIDER;
                }
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return locationProvider;
    }

    // Xử lý button hoàn tất
    public void btnDone(View view) {
        //Đóng tất cả các màn hình khác, về màn hình chính
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
