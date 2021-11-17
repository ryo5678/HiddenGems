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
           public void onMapReady(@NonNull GoogleMap maps) {
               LatLng UNCC = new LatLng(35.303555, -80.73238);
               maps.addMarker(new MarkerOptions()
                    .position(UNCC)
                    .title("UNCC Marker"));
               maps.moveCamera(CameraUpdateFactory.newLatLng(UNCC));


               maps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                   @Override
                   public void onMapClick(@NonNull LatLng latLng) {
                       MarkerOptions marker = new MarkerOptions();
                       marker.position(latLng);
                       marker.title(latLng.latitude + " : " + latLng.longitude);
                       maps.clear();
                       maps.animateCamera(CameraUpdateFactory.newLatLngZoom(
                               latLng, 10
                       ));
                       maps.addMarker(marker);
                   }
               });
           }
       });

        return view;

    }
}