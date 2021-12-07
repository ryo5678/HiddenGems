package com.example.hiddengems.map;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.search.SearchResultsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapFragment extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SearchResultsFragment.location){
            action = (SearchResultsFragment.location) context;
        }
    }

    public static SearchResultsFragment.location action;

    public interface location{
        void showLocation(String id);
    }

    ArrayList<Locations.Location> allLocations = new ArrayList<>();
    String SelectedFilter = null;
    GoogleMap ourMaps;
    ArrayList<Marker> markers = new ArrayList<>();
    //List<Long> ratings = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SelectedFilter = (String) item.getTitle();
        if (notTitle(SelectedFilter)) {
            getActivity().setTitle("Filter Markers by: " + SelectedFilter);
            for(int x=0; x < markers.size(); x++) {
                for(int y=0; y < allLocations.size(); y++){
                    if(allLocations.get(y).getName().equals(markers.get(x).getTitle())) {
                        if(allLocations.get(y).getCategory().equals(SelectedFilter) ||
                                checkRating(SelectedFilter, allLocations.get(y)) ||
                                checkSeason(SelectedFilter,allLocations.get(y)) ||
                                checkTags(SelectedFilter, allLocations.get(y))) {

                            markers.get(x).setVisible(true);
                        } else {
                            markers.get(x).setVisible(false);
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }



    private boolean checkRating(String filter, Locations.Location location) {

        return false;
    }

    private boolean checkSeason(String filter, Locations.Location location) {

        if (location.getSeason().equals(filter)) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkTags(String filter, Locations.Location location) {

        for(int x=0; x < location.Tags.size(); x++) {
            if (location.Tags.get(x).equals(filter)) {
                return true;
            }
        }

        return false;
    }


    public boolean notTitle(String selectedItem) {

        if (selectedItem.equals("Start Menu")) {
            return false;
        } else if (selectedItem.equals("Categories")) {
            return false;
        } else if (selectedItem.equals("Tags")) {
            return false;
        }else if (selectedItem.equals("Ratings")) {
            return false;
        }else if (selectedItem.equals("Seasons")) {
            return false;
        }else if (selectedItem.equals("Reset")) {
            for (int x=0; x < markers.size(); x++) {
                markers.get(x).setVisible(true);
            }
            getActivity().setTitle("Map");
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Map");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("locations")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        allLocations.clear();
                        for(QueryDocumentSnapshot document : value) {
                            if (document.getGeoPoint("Coordinates") != null) {
                                Locations.Location newPlace = new Locations.Location(document.getString("Name"), document.getString("Address"), document.getString("Category"));
                                newPlace.setDocID(document.getId());
                                newPlace.setTags((ArrayList<String>) document.get("Tags"));
                                GeoPoint geoPoint = document.getGeoPoint("Coordinates");
                                LatLng latLng = new LatLng((double) geoPoint.getLatitude(), (double) geoPoint.getLongitude());
                                newPlace.setCoordinates(latLng);
                               // ArrayList<String> tempList = (ArrayList<String>) document.get("ratings");
                                //for (int i = 0; i < tempList.size(); i++){
                                  //  if (i == 0) {

                                   // } else if (i%2 == 1){
                                     //   ratings.add(Long.parseLong(tempList.get(i)));
                                   //     Log.d("Test","adding to ratings");
                                 //   }

                               // }
                               // int num = 0;
                               // for(int x=0; x<ratings.size();x++) {
                                 //   num += ratings.get(x);
                               // }
                                //num /= (ratings.size()/2);
                                Random rand = new Random();
                                newPlace.setCurrentRating(0);
                                Log.d("Maps", "Adding Location");
                                Log.d("Maps", ("Coordinates: " + newPlace.getCoordinates().toString()));
                                allLocations.add(newPlace);
                            }
                        }
                    }
                });


        //view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

       SupportMapFragment supportMapFragment = (SupportMapFragment)
               getChildFragmentManager().findFragmentById(R.id.google_map);

       supportMapFragment.getMapAsync(new OnMapReadyCallback() {
           @Override
           public void onMapReady(@NonNull GoogleMap maps) {
               ourMaps = maps;
               LatLng UNCC = new LatLng(35.303555, -80.73238);
               ourMaps.addMarker(new MarkerOptions()
                    .position(UNCC)
                    .title("UNCC Marker"));
               Log.d("Maps", String.valueOf(allLocations.size()));
               for (int x=0; x<allLocations.size(); x++) {
                   Log.d("Maps","Adding Location to Map");
                  Marker newMarker = ourMaps.addMarker(new MarkerOptions()
                                     .position(allLocations.get(x).getCoordinates())
                                     .title(allLocations.get(x).getName())
                                     .snippet("Rating: " + allLocations.get(x).getCurrentRating() + " / 5")
                                     .alpha(1));
                  newMarker.setTag(0);
                  markers.add(newMarker);
               }

               //ourMaps.moveCamera(CameraUpdateFactory.newLatLng(UNCC));
               LatLng NoDa = new LatLng(35.2456, -80.8018);
               ourMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(NoDa, 13));

               ourMaps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                   @Override
                   public void onMapClick(@NonNull LatLng coordinates) {
                       ourMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(
                               coordinates, 10
                       ));
                   }
               });

               ourMaps.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                   @Override
                   public boolean onMarkerClick(@NonNull Marker marker) {

                       Integer clickCount = (Integer) marker.getTag();

                       if (clickCount == 0) {
                           marker.setTag(1);
                           return false;
                       } else {
                           boolean check = true;
                           for(int y=0; y < allLocations.size(); y++) {
                               if (allLocations.get(y).getName().equals(marker.getTitle())) {
                                   marker.setTag(0);
                                   action.showLocation(allLocations.get(y).docID);
                                   check = true;
                               } else
                                   check = false;
                           }

                           return check;
                       }

                   }
               });
           }
       });
        return view;

    }
}