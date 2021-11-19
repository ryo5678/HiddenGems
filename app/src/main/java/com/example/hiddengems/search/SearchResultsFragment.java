package com.example.hiddengems.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hiddengems.R;

import com.example.hiddengems.account.LoginFragment;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.dataModels.Locations.*;
import com.example.hiddengems.dataModels.Person;
import com.example.hiddengems.databinding.FragmentSearchResultsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment {

    private final String TAG = "TAG";
    ArrayList<Location> locationList = new ArrayList<>();
    FragmentSearchResultsBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    LocationRecyclerViewAdapter adapter;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchResultsFragment newInstance(ArrayList<Location> Locations) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putSerializable("searchList",Locations);
        Log.d("TAG",Locations.get(0).toString());
        Log.d("SIZE",String.valueOf(Locations.size()));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //locationList.clear();
            locationList = (ArrayList<Location>)getArguments().getSerializable("searchList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchResultsBinding.inflate(inflater,container,false);


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // locationList.clear();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Search Results");
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LocationRecyclerViewAdapter(locationList);
        recyclerView.setAdapter(adapter);

    }

    public static class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder> {
        ArrayList<Location> Locations;
        int count = 0;
        List<Integer> ratings = new ArrayList<>();
        int total = 0;
        public LocationRecyclerViewAdapter(ArrayList<Location> data) {
            this.Locations = data;
        }
        @NonNull
        @Override
        public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locations_row_list,parent, false);
            LocationViewHolder viewHolder = new LocationViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull LocationViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Location location = Locations.get(position);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("locations").document(location.docID).collection("reviews")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (QueryDocumentSnapshot document : value) {
                                count +=1;
                            }
                        }
                    });
            DocumentReference docRef = db.collection("locations").document(location.docID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            ArrayList<String> tempList = (ArrayList<String>) document.get("ratings");
                            for (int i = 0; i < tempList.size(); i++){
                                if (i%2 == 1) {
                                    ratings.add(Integer.parseInt(tempList.get(i)));
                                }

                            }
                            for (int j = 0; j< ratings.size(); j++) {
                                total += ratings.get(j);
                            }

                            total /= ratings.size();
                            location.setCurrentRating(total);
                            location.setNumberofRatings(ratings.size());
                        } else {
                        }
                    } else {
                    }
                }
            });

            holder.position = position;
            holder.nameView.setText(location.getName());
            holder.rateView.setText("Current rating: " + location.getCurrentRating());
            holder.reView.setText("Total reviews: " + count);
            // Not working yet for images, holder.preView.set


        }

        @Override
        public int getItemCount() {
            return this.Locations.size();
        }

        public class LocationViewHolder extends RecyclerView.ViewHolder {
            int position;
            View rootView;
            TextView nameView;
            TextView rateView;
            TextView reView;
            ImageView preView;


            public LocationViewHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
                nameView = itemView.findViewById(R.id.locationNameView);
                rateView = itemView.findViewById(R.id.locationRatingView);
                reView = itemView.findViewById(R.id.locationReview);
                preView = itemView.findViewById(R.id.locationPreview);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        action.showLocation(Locations.get(position).docID);
                    }
                });

            }


        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof location){
            action = (location) context;
        }
    }

    public static location action;

    public interface location{
        void showLocation(String id);
    }



}