package com.example.hiddengems.search;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiddengems.AddScreenFragment;
import com.example.hiddengems.R;

import com.example.hiddengems.dataModels.Locations.*;
import com.example.hiddengems.databinding.FragmentEditLocationBinding;
import com.example.hiddengems.databinding.FragmentLocationBinding;
import com.example.hiddengems.profile.EditProfileFragment;
import com.example.hiddengems.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class editLocationFragment extends Fragment {

    FragmentEditLocationBinding binding;
    Location location;
    String id;
    List<Integer> ratings = new ArrayList<>();
    int rating = 0;
    ArrayList<Review> reviews = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ReviewRecyclerViewAdapter adapter;
    FirebaseAuth mAuth;
    String reviewID;
    FirebaseUser user;
    int total = 0;
    int allRatings = 0;
    boolean isMod;
    DocumentReference documentReference;


    public editLocationFragment(DocumentReference docRef) {
        this.documentReference = docRef;

    }


    public static editLocationFragment newInstance(String id) {
        editLocationFragment fragment = new editLocationFragment(newInstance(id).documentReference);
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
        binding = FragmentEditLocationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        /* Moderator Code Start */

        if(user.getUid().equals("YPKp0avJHTPNI30gT7Rcgf9jme62")) {
            isMod = true;
        } else {
            isMod = false;
        }

        if(isMod == true) {
            binding.deleteLocation.setVisibility(View.VISIBLE);
        } else {
            binding.deleteLocation.setVisibility(View.GONE);
        }

        binding.deleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("locations").document(documentReference.getId());
                docRef.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        /* Moderator Code End */

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("locations").document(documentReference.getId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        ArrayList<String> tempList = (ArrayList<String>) document.get("ratings");
                        if (tempList != null && tempList.size() != 0) {
                            for (int i = 0; i < tempList.size(); i++) {
                                if (i % 2 == 1) {
                                    ratings.add(Integer.parseInt(tempList.get(i)));
                                }

                            }
                            for (int j = 0; j < ratings.size(); j++) {
                                total += ratings.get(j);
                            }

                            total /= ratings.size();
                        }

                        location = new Location(document.getString("Name"),document.getString("Address")
                                ,document.getString("Category"),document.getString("Description"),
                                document.getString("time"),total,ratings.size(),
                                (ArrayList<String>)document.get("Tags"));
                        location.setViews(Math.toIntExact((Long) document.get("Views")));
                        location.setViews(location.getViews() + 1);
                        String tags = location.getTags().toString().replace("[","")
                                .replace("]","").trim();

                        docRef.update("Views",location.getViews());
                        getActivity().setTitle(location.getName());
                        binding.locationViewName.setText(location.getName());
                        binding.locationDescription.setText(location.getDescription());
                        binding.locationCategory.setText(location.getCategory());
                        binding.locationTags.setText(tags);
                        binding.locationAddress.setText(location.getAddress());
                        binding.ratingAverageOutput.setText(String.valueOf(total));

                    } else {
                    }
                } else {
                }

            }
        });

        binding.saveEdits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = binding.locationDescription.getText().toString();
                String category = binding.locationCategory.getText().toString();
                String tags = binding.locationTags.getText().toString();
                String address = binding.locationAddress.getText().toString();

                if(description.isEmpty()  || category.isEmpty() || tags.isEmpty() || address.isEmpty()) {
                    missingInput(getActivity());
                }
                else {
                    //add code to update firebase
                    String newDes = binding.locationDescription.getText().toString();
                    String newCat = binding.locationCategory.getText().toString();
                    String listTags = binding.locationTags.getText().toString();
                    ArrayList<String> newTags = new ArrayList<String>(Arrays.asList(listTags.split(",")));
                    String newAdd = binding.locationAddress.getText().toString();

                    HashMap<String, Object> editLoc = new HashMap<>();
                    editLoc.put("Description",newDes);
                    editLoc.put("Category",newCat);
                    editLoc.put("Tags", newTags);
                    editLoc.put("Address", newAdd);

                    db.collection("locations").document(documentReference.getId())
                            .set(editLoc, SetOptions.merge())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                    action.returnLocation(documentReference.getId());


                }
            }
        });



        binding.ratingBar.setEnabled(false);
        db.collection("locations").document(documentReference.getId()).collection("reviews")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        reviews.clear();
                        for(QueryDocumentSnapshot document : value){
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");
                            reviews.add(new Review(document.getString("creator"), document.getString("review"),
                                    dateFormat.format(document.getTimestamp("time_created").toDate()), document.getId()));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        adapter = new ReviewRecyclerViewAdapter(reviews);
        recyclerView = binding.reviewRecycler;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        binding.addReview.setEnabled(false);

        binding.reportButton.setEnabled(false);

    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof editLocation) {
            action = (editLocation) context;
        }
    }

    public static editLocation action;

    public interface editLocation{
        void returnLocation(String id);
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
            Review review = items.get(position);

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            holder.position = position;
            holder.nameView.setText(review.creator);
            holder.reviewView.setText(review.review);
            holder.dateView.setText(review.date);

            if(review.creator.equals(user.getDisplayName()) || isMod == true) {
                holder.imageView3.setVisibility(View.VISIBLE);
            } else {
                holder.imageView3.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        public class ReviewViewHolder extends RecyclerView.ViewHolder {
            int position;
            View rootView;
            TextView nameView;
            TextView reviewView;
            TextView dateView;
            ImageView imageView3;


            public ReviewViewHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
                nameView = itemView.findViewById(R.id.reviewName);
                reviewView = itemView.findViewById(R.id.reviewDesc);
                dateView = itemView.findViewById(R.id.reviewDate);
                imageView3 = itemView.findViewById(R.id.imageView3);

                itemView.findViewById(R.id.reviewName).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                itemView.findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference docRef = db.collection("locations").document(id);
                        docRef.collection("reviews").document(items.get(position).reviewID)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                });
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