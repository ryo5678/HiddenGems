package com.example.hiddengems.search;

import android.annotation.SuppressLint;
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

import com.example.hiddengems.dataModels.Locations.*;
import com.example.hiddengems.databinding.FragmentAddScreenBinding;
import com.example.hiddengems.databinding.FragmentLocationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationFragment extends Fragment {

    FragmentLocationBinding binding;
    Location location;
    String id;
    List<Integer> ratings = new ArrayList<>();
    int rating = 0;
    ArrayList<Review> reviews = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ReviewRecyclerViewAdapter adapter;


    public LocationFragment() {
        // Required empty public constructor
    }


    public static LocationFragment newInstance(String id) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString("Location",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("Location");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("locations").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<String> tempList = (ArrayList<String>) document.get("ratings");
                        for (int i = 0; i < ratings.size(); i++){
                            if (i == 0) {

                            }else if (i%2 == 0) {

                            } else {
                                ratings.add(Integer.parseInt(tempList.get(i)));
                            }

                        }
                        location = new Location(document.getString("Name"),document.getString("Address")
                                ,document.getString("Category"),document.getString("Description"),
                                document.getString("time"),rating,ratings.size(),
                                (ArrayList<String>)document.get("Tags"));

                        getActivity().setTitle(location.getName());
                        binding.locationViewName.setText(location.getName());
                        binding.locationDescription.setText(location.getDescription());
                        binding.locationCategory.setText(location.getCategory());
                        binding.locationTags.setText(location.getTags().toString());
                        binding.locationAddress.setText(location.getAddress());

                    } else {
                    }
                } else {
                }
            }
        });
        db.collection("locations").document(id).collection("reviews")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(QueryDocumentSnapshot document : value){
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
                            reviews.add(new Review(document.getString("Creator"), document.getString("review"),
                                    dateFormat.format(document.getTimestamp("time_created").toDate()), document.getId()));
                        }
                         //adapter.notifyDataSetChanged();
                    }
                });

        /*adapter = new LocationRecyclerViewAdapter(location);
        recyclerView = binding.locationRecycler;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/

        /* Code for submitting a review
        HashMap<String, Object> review = new HashMap<>();
        review.put("creator","");
        review.put("review","");
        review.put("time_created","");

        docRef.collection("reviews")
                .add(review)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                });*/
    }
    public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {
        ArrayList<Review> items;
        public ReviewRecyclerViewAdapter(ArrayList<Review> data) {
            this.items = data;
        }
        @NonNull
        @Override
        public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_main_view,parent, false);
            ReviewViewHolder viewHolder = new ReviewViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewViewHolder holder, @SuppressLint("RecyclerView") int position) {
            //Location location = item;

            holder.position = position;
        }

        @Override
        public int getItemCount() {
            return 1;
        }

        public class ReviewViewHolder extends RecyclerView.ViewHolder {
            int position;
            View rootView;


            public ReviewViewHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
            }
        }
    }

    public class Review {
        String creator;
        String review;
        String date;
        String reviewID;

        public Review(String creator, String review, String date, String reviewID) {
            this.creator = creator;
            this.review = review;
            this.date = date;
            this.reviewID = reviewID;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReviewID() {
            return reviewID;
        }

        public void setReviewID(String reviewID) {
            this.reviewID = reviewID;
        }
    }
}