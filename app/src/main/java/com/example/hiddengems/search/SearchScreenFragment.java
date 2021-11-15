package com.example.hiddengems.search;

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
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.dataModels.Locations.*;
import com.example.hiddengems.dataModels.Person;
import com.example.hiddengems.databinding.FragmentSearchScreenBinding;
import com.example.hiddengems.profile.ProfileFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class SearchScreenFragment extends Fragment {

    private final String TAG = "TAG";
    String SelectedFilter = null;
    FragmentSearchScreenBinding binding;
    String text;
    ArrayList<Location> allLocations = Locations.getLocations("Locations");
    ArrayList<Location> foundLocations = new ArrayList<Location>();


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
                            if (SelectedFilter != null && (allLocations.get(x).getCategory().equals(SelectedFilter) || checkTags() || checkSeason() || checkRating() )) {
                                foundLocations.add(allLocations.get(x));
                            } else if (SelectedFilter == null){
                                foundLocations.add(allLocations.get(x));
                                Log.d(TAG, String.valueOf(foundLocations.size()));
                            }
                        }
                    }
                    if(foundLocations.size() != 0 && foundLocations != null) {
                        Log.d(TAG, String.valueOf(foundLocations.size()));
                        Log.d(TAG,foundLocations.get(0).toString());
                        action.searchResults(foundLocations);
                    } else {
                        Toast.makeText(getActivity(), "No Matching Locations Found",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean checkRating() {

        return false;
    }

    private boolean checkSeason() {

        return false;
    }

    private boolean checkTags() {

        return false;
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
        }else  {
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