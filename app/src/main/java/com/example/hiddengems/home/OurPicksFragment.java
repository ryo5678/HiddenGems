package com.example.hiddengems.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.FragmentMyGemsBinding;
import com.example.hiddengems.databinding.FragmentOurPicksBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class OurPicksFragment extends Fragment {

    FragmentOurPicksBinding binding;
    FirebaseAuth mAuth;
    ArrayList<Locations.Location> allLocations = new ArrayList<>();
    ArrayList<Locations.Location> finalLocations = new ArrayList<>();

    public OurPicksFragment() {
        // Required empty public constructor
    }


    public static OurPicksFragment newInstance() {
        OurPicksFragment fragment = new OurPicksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOurPicksBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("locations")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        allLocations.clear();
                        for(QueryDocumentSnapshot document : value) {


                            Locations.Location newPlace = new Locations.Location(document.getId(), document.getString("Name")
                                    ,document.getString("Category"));
                            Log.d("Check","Adding Location");
                            allLocations.add(newPlace);
                        }
                    }
                });

        mAuth = FirebaseAuth.getInstance();
        for (int i = 0; i < allLocations.size(); i++) {
            String match = allLocations.get(i).Name;
            if (match == "Heist Brewery" || match == "Haberdish") {
                finalLocations.add(allLocations.get(i));
            }
        }

    }
}