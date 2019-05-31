package com.example.admin.quanlycafe.FRAGMENT;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.quanlycafe.MainActivity;
import com.example.admin.quanlycafe.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private static LocationFragment instance;

    public static LocationFragment Instance() {
        if (instance == null) {
            instance = new LocationFragment();
        }
        return instance;
    }

    private void Instance(LocationFragment instance) {
        LocationFragment.instance = instance;
    }

    private GoogleMap googleMap;
    private LocationManager service;

    private static View view;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("Vị Trí");
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_location, container, false);
            MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        catch (InflateException e) {
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//bang M
            if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                XuLyQuyen();
            } else {
                ActivityCompat.requestPermissions(this.getActivity(), new
                        String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            XuLyQuyen();
        }
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void XuLyQuyen()
    {
        this.googleMap.setMyLocationEnabled(true);
        service = (LocationManager)getActivity().getSystemService(getContext().LOCATION_SERVICE);
        Location location= service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null)
        {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CreateMarker(latLng);
            this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }

    }

    private void CreateMarker(LatLng latLng)
    {
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Cà Phê Thanh Long")
                .snippet("160B, đường 3/2, phường 10, quận 10, tp HCM")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        );
    }
}
