package com.example.hiddengems.search;

import android.content.Context;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.dataModels.Locations.*;
import com.example.hiddengems.dataModels.Person;
import com.example.hiddengems.databinding.FragmentSearchScreenBinding;
import com.example.hiddengems.profile.ProfileFragment;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchScreenFragment extends Fragment {

    private final String TAG = "TAG";
    String SelectedFilter = null;
    FragmentSearchScreenBinding binding;
    String text;
    ArrayList<Location> allLocations = new ArrayList<>();
    ArrayList<Location> foundLocations = new ArrayList<Location>();
    List<Integer> ratings = new ArrayList<>();



    public SearchScreenFragment() {
        // Required empty public constructor
    }

    public static SearchScreenFragment newInstance() {
        SearchScreenFragment fragment = new SearchScreenFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        foundLocations.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchScreenBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("locations")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        allLocations.clear();
                        for(QueryDocumentSnapshot document : value) {
                            Location newPlace = new Location(document.getString("Name"),
                                    document.getString("Address"),document.getString("Category"));
                            newPlace.setTags((ArrayList<String>) document.get("Tags"));
                            newPlace.setSeason(document.getString("Season"));
                            newPlace.setDocID(document.getId());
                            ArrayList<String> tempList = (ArrayList<String>) document.get("ratings");
                            for (int i = 1; i < tempList.size(); i+=2){

                                ratings.add(Integer.parseInt(tempList.get(i)));

                            }
                            int num = 0;
                            for(int x=0; x<ratings.size();x++) {
                                num += ratings.get(x);
                            }
                            if (ratings.size() == 0){
                                num = 0;
                            } else {
                                num /= ratings.size();
                            }


                            newPlace.setCurrentRating(num);
                            newPlace.setChain((Boolean) document.get("is_Chain"));
                            newPlace.setVerified((Boolean) document.get("Verified"));
                            newPlace.setViews(Math.toIntExact((Long) document.get("Views")));
                            newPlace.setHiddenGem(num, newPlace.getViews(), newPlace.getVerified(), newPlace.isChain() );
                            ratings.clear();
                            allLocations.add(newPlace);
                        }
                    }
                });

        binding.searchSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text = binding.enterLocation.getText().toString();
                if (text.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    for(int x=0;x < allLocations.size(); x++) {
                        Log.d(TAG, "in loop: " + x);
                        if(allLocations.get(x).getName().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                            if (SelectedFilter != null &&   (allLocations.get(x).getCategory().equals(SelectedFilter) ||
                                                            checkTags(SelectedFilter, allLocations.get(x)) ||
                                                            checkSeason(SelectedFilter,allLocations.get(x)) ||
                                                            checkRating(SelectedFilter, allLocations.get(x)) ||
                                                            checkIfGem(SelectedFilter, allLocations.get(x)) )) {
                                foundLocations.add(allLocations.get(x));
                            } else if (SelectedFilter == null){
                                foundLocations.add(allLocations.get(x));
                                Log.d(TAG, String.valueOf(foundLocations.size()));
                            }
                        }
                    }
                }
                if(foundLocations.size() != 0) {
                    Log.d(TAG, String.valueOf(foundLocations.size()));
                    Log.d(TAG,foundLocations.get(0).toString());
                    action.searchResults(foundLocations);
                } else {
                    Log.d("No result", "no results");
                    Toast.makeText(getActivity(), "No Matching Locations Found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkRating(String filter, Location location) {

        return false;
    }

    private boolean checkSeason(String filter, Location location) {

        if (location.getSeason().equals(filter)) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkTags(String filter, Location location) {

        for(int x=0; x < location.Tags.size(); x++) {
            if (location.Tags.get(x).equals(filter)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkIfGem(String filter, Location location) {
        Log.d("Gem", filter);
        Log.d("Gem", location.getHiddenGem().toString());
        if (location.getHiddenGem() && filter.equals("Hidden Gems")) {
            Log.d("Gem", "true");
            return true;
        } else {
            Log.d("Gem", "false");
            return false;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SelectedFilter = (String) item.getTitle();
        //Log.d("selecteditem", SelectedFilter);
        if (notTitle(SelectedFilter)) {
            getActivity().setTitle("Search by: " + SelectedFilter);
        }
        return super.onOptionsItemSelected(item);
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
        } else if (selectedItem.equals("Reset")) {
            SelectedFilter = null;
            getActivity().setTitle("Search");
            return false;
        } else  {
            return true;
        }

    }


    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof results){
            action = (results) context;
        }
    }

    public static results action;

    public interface results{
        void searchResults(ArrayList<Location> locations);
    }

    @Override
    public void onResume() {
        super.onResume();
        foundLocations.clear();
    }
}