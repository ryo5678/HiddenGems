package com.example.hiddengems.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiddengems.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

       SupportMapFragment supportMapFragment = (SupportMapFragment)
               getChildFragmentManager().findFragmentById(R.id.google_map);

       supportMapFragment.getMapAsync(new OnMapReadyCallback() {
           @Override
           public void onMapReady(@NonNull GoogleMap googleMap) {
               LatLng UNCC = new LatLng(35.303555, -80.73238);
               googleMap.addMarker(new MarkerOptions()
                    .position(UNCC)
                    .title("UNCC Marker"));
               googleMap.moveCamera(CameraUpdateFactory.newLatLng(UNCC));


               googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                   @Override
                   public void onMapClick(@NonNull LatLng latLng) {
                       MarkerOptions markerOptions = new MarkerOptions();
                       markerOptions.position(latLng);
                       markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                       googleMap.clear();
                       googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                               latLng, 10
                       ));
                       googleMap.addMarker(markerOptions);
                   }
               });
           }
       });

        return view;

    }
}