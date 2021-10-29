package com.example.hiddengems.search;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.FragmentSearchScreenBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchScreenFragment extends Fragment {

    String SelectedFilter = null;
    FragmentSearchScreenBinding binding;
    String text;
    ArrayList<Locations.Location> allLocations;

    public SearchScreenFragment() {
        // Required empty public constructor
    }



    public static SearchScreenFragment newInstance(ArrayList<Locations.Location> allLocations) {
        SearchScreenFragment fragment = new SearchScreenFragment();
        Bundle args = new Bundle();
        args.putSerializable("allLocations",allLocations);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            allLocations = (ArrayList<Locations.Location>) getArguments().getSerializable("allLocations");
        }
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
                    ArrayList<Locations.Location> results = submitLocation(text);
                    //goToSearchResultsFragment(results);
                }
            }
        });
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

    public ArrayList<Locations.Location> submitLocation (String text){
        ArrayList<Locations.Location> foundLocations = new ArrayList<Locations.Location>();
        for(int x=0;x < allLocations.size(); x++) {
            if(allLocations.get(x).getName().contains(text)) {
                if (SelectedFilter != null && allLocations.get(x).getCategory().equals(SelectedFilter)) {
                    foundLocations.add(allLocations.get(x));
                } else {
                    foundLocations.add(allLocations.get(x));
                }
            }
        }

        return foundLocations;
    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }

}