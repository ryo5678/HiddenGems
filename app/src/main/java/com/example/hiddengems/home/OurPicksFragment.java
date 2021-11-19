package com.example.hiddengems.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.FragmentMyGemsBinding;
import com.example.hiddengems.databinding.FragmentOurPicksBinding;
import com.example.hiddengems.profile.MyGemsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.hiddengems.dataModels.Locations.*;

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
    public static class GemsRecyclerViewAdapter extends RecyclerView.Adapter<GemsRecyclerViewAdapter.GemsViewHolder> {
        ArrayList<Location> Locations;
        public GemsRecyclerViewAdapter(ArrayList<Location> data) {
            this.Locations = data;
        }
        @NonNull
        @Override
        public GemsRecyclerViewAdapter.GemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locations_row_list,parent, false);
           GemsRecyclerViewAdapter.GemsViewHolder viewHolder = new GemsRecyclerViewAdapter.GemsViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GemsRecyclerViewAdapter.GemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
            com.example.hiddengems.dataModels.Locations.Location location = Locations.get(position);
            holder.position = position;
            holder.nameView.setText(location.getName());
            holder.rateView.setText("Current rating: " + location.getCurrentRating());
            holder.reView.setText("Total ratings: " + location.getNumberofRatings());
            // Not working yet for images, holder.preView.set


        }

        @Override
        public int getItemCount() {
            return this.Locations.size();
        }

        public class GemsViewHolder extends RecyclerView.ViewHolder {
            int position;
            View rootView;
            TextView nameView;
            TextView rateView;
            TextView reView;
            ImageView preView;


            public GemsViewHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
                nameView = itemView.findViewById(R.id.locationNameView);
                rateView = itemView.findViewById(R.id.locationRatingView);
                reView = itemView.findViewById(R.id.locationReview);
                preView = itemView.findViewById(R.id.locationPreview);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //action.showLocation(Locations.get(position).docID);
                    }
                });

            }


        }
    }
}