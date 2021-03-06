package com.moutamid.restaurantproject;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private Geocoder geocoder;
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    Marker userLocationMarker;
    Circle userLocationAccuracyCircle;

    LatLng warongCheLatLng;
    MarkerOptions warongCheMarkerOptions;

    LatLng hRSteakLatLng;
    MarkerOptions hRSteakMarkerOptions;

    LatLng pakHsnLatLng;
    MarkerOptions pakHsnMarkerOptions;

    int value = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        value = getIntent().getIntExtra("value", 1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //      mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
            zoomToUserLocation();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We can show user a dialog why this permission is necessary
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }

        }

        pakHsnLatLng = new LatLng(3.1597351, 101.702037);
        pakHsnMarkerOptions = new MarkerOptions()
                .position(pakHsnLatLng)
                .title("Pak Hassan")
                .snippet("Best personal restaurant!");

        hRSteakLatLng = new LatLng(3.1606795, 101.6983993);
        hRSteakMarkerOptions = new MarkerOptions()
                .position(hRSteakLatLng)
                .title("HR Steak House")
                .snippet("Best Steak Restaurant!");

        warongCheLatLng = new LatLng(3.1607474, 101.7012206);
        warongCheMarkerOptions = new MarkerOptions()
                .position(warongCheLatLng)
                .title("Warong Che Senah")
                .snippet("Best Che Restaurant!");

        if (value == 1)
            addFirstBtnMarkers();

        if (value == 2)
            addSecondBtnMarkers();

        if (value == 3)
            addThirdBtnMarkers();


//        try {
//            List<Address> addresses = geocoder.getFromLocationName("london", 1);
//            if (addresses.size() > 0) {
//                Address address = addresses.get(0);
//                LatLng london = new LatLng(address.getLatitude(), address.getLongitude());
//                MarkerOptions markerOptions = new MarkerOptions()
//                        .position(london)
//                        .title(address.getLocality());
//                mMap.addMarker(markerOptions);
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void addFirstBtnMarkers() {

        mMap.addMarker(pakHsnMarkerOptions);
        mMap.addMarker(hRSteakMarkerOptions);
        mMap.addMarker(warongCheMarkerOptions).showInfoWindow();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(warongCheLatLng, 25);//16
        mMap.animateCamera(cameraUpdate);

    }

    private void addSecondBtnMarkers() {

        mMap.addMarker(pakHsnMarkerOptions);
        mMap.addMarker(hRSteakMarkerOptions).showInfoWindow();
        mMap.addMarker(warongCheMarkerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(hRSteakLatLng, 25);//16
        mMap.animateCamera(cameraUpdate);

    }

    private void addThirdBtnMarkers() {

        mMap.addMarker(pakHsnMarkerOptions).showInfoWindow();
        mMap.addMarker(hRSteakMarkerOptions);
        mMap.addMarker(warongCheMarkerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pakHsnLatLng, 25);//16
        mMap.animateCamera(cameraUpdate);

    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Log.d(TAG, "onLocationResult: " + locationResult.getLastLocation());
            if (mMap != null) {
                setUserLocationMarker(locationResult.getLastLocation());
            }
        }
    };

    private void setUserLocationMarker(Location location) {

//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        if (userLocationMarker == null) {
//            //Create a new marker
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
////            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.redcar));
////            markerOptions.rotation(location.getBearing());
////            markerOptions.anchor((float) 0.5, (float) 0.5);
//            userLocationMarker = mMap.addMarker(markerOptions);
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
//        } else {
//            //use the previously created marker
//            userLocationMarker.setPosition(latLng);
//            userLocationMarker.setRotation(location.getBearing());
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
//        }
//
//        if (userLocationAccuracyCircle == null) {
//            CircleOptions circleOptions = new CircleOptions();
//            circleOptions.center(latLng);
////            circleOptions.strokeWidth(4);
////            circleOptions.strokeColor(Color.argb(255, 255, 0, 0));
////            circleOptions.fillColor(Color.argb(32, 255, 0, 0));
//            circleOptions.radius(location.getAccuracy());
//            userLocationAccuracyCircle = mMap.addCircle(circleOptions);
//        } else {
//            userLocationAccuracyCircle.setCenter(latLng);
//            userLocationAccuracyCircle.setRadius(location.getAccuracy());
//        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
            stopLocationUpdates();
        } else {
            // you need to request permissions...
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void zoomToUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
             //   LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
//                Toast.makeText(MapsActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
//                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d(TAG, "onMapLongClick: " + latLng.toString());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String streetAddress = address.getAddressLine(0);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(streetAddress)
                        .draggable(true)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d(TAG, "onMarkerDragStart: ");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG, "onMarkerDrag: ");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d(TAG, "onMarkerDragEnd: ");
        LatLng latLng = marker.getPosition();
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String streetAddress = address.getAddressLine(0);
                marker.setTitle(streetAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
                zoomToUserLocation();
            } else {
                //We can show a dialog that permission is not granted...
            }
        }
    }

}