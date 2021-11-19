

package com.example.hiddengems.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.FragmentHoursChangeBinding;
import com.example.hiddengems.databinding.FragmentLocationRemovalBinding;
import com.example.hiddengems.databinding.FragmentMyGemsBinding;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;


public class MyGemsFragment extends Fragment {

    FragmentMyGemsBinding binding;
    String text;
    FirebaseAuth mAuth;
    ArrayList<Locations.Location> allLocations = new ArrayList<>();

    public MyGemsFragment() {
        // Required empty public constructor
    }


    public static MyGemsFragment newInstance() {
        MyGemsFragment fragment = new MyGemsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        binding = FragmentMyGemsBinding.inflate(inflater, container, false);

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
                            //mauth.getuser()

                            Locations.Location newPlace = new Locations.Location(document.getString("Name"),document.getString("Address"),document.getString("Category"));
                            newPlace.setDocID(document.getId());
                            Log.d("Check","Adding Location");
                            allLocations.add(newPlace);


                        }
                    }
                });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String name = user.getDisplayName();






        getActivity().setTitle("My Gems");

        binding.myGems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = binding.gemss.getText().toString();
                if (text.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    submitTags(text);
                }
            }
        });



    }

    public void submitTags(String text) {

        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack("gems", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                /*}
            }
        });*/
    }



    public void missingInput(Context context) {
        Toast.makeText(context, getString(R.string.missing), Toast.LENGTH_SHORT).show();
    }
}
