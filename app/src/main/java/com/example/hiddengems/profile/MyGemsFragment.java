

package com.example.hiddengems.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations.*;
import com.example.hiddengems.databinding.FragmentHoursChangeBinding;
import com.example.hiddengems.databinding.FragmentLocationRemovalBinding;
import com.example.hiddengems.databinding.FragmentMyGemsBinding;

import com.example.hiddengems.search.SearchResultsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
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
    ArrayList<Location> allLocations = new ArrayList<>();
    ArrayList<Location> finalLocations = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    GemsRecyclerViewAdapter adapter;

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

        Log.d("TAG","Before db");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("locations")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Log.d("TAG","Before clear");
                        allLocations.clear();
                        for (QueryDocumentSnapshot document : value) {


                            Location newPlace = new Location(document.getId(), document.getString("Creator"),
                                    document.getString("Name"), document.getString("Category"),
                                    (ArrayList<String>) document.get("Tags"));

                            Log.d("TAG", "Adding Location");
                            allLocations.add(newPlace);


                        }
                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        String name = user.getUid();
                        Log.d("TAG",name);
                        Log.d("TAG",allLocations.toString());
                        Log.d("TAG",allLocations.get(0).Creator);
                        for (int i = 0; i < allLocations.size(); i++) {

                            if (allLocations.get(i).Creator.equals(name)) {
                                finalLocations.add(allLocations.get(i));
                                Log.d("TAG",allLocations.get(0).Creator);

                            }
                        }

                        getActivity().setTitle("My Gems");
                        Log.d("TAG",finalLocations.toString());
                        recyclerView = binding.myGemsRecycler;
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new GemsRecyclerViewAdapter(finalLocations);
                        recyclerView.setAdapter(adapter);
                    }

                });

                binding.editButtonGems2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getActivity()
                                .getSupportFragmentManager();
                        fm.popBackStack("gems", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    }
                });

    }

    public void submitGems(String text) {

        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack("gems", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                /*}
            }
        });*/
    }

    public static class GemsRecyclerViewAdapter extends RecyclerView.Adapter<GemsRecyclerViewAdapter.GemsViewHolder> {
        ArrayList<Location> Locations;
        public GemsRecyclerViewAdapter(ArrayList<Location> data) {
            this.Locations = data;
        }
        @NonNull
        @Override
        public GemsRecyclerViewAdapter.GemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mygems_view,parent, false);
            GemsRecyclerViewAdapter.GemsViewHolder viewHolder = new GemsRecyclerViewAdapter.GemsViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GemsRecyclerViewAdapter.GemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Location location = Locations.get(position);
            holder.position = position;
            holder.nameView.setText(location.getName());
            holder.categoryView.setText(location.getCategory());
            holder.tagsView.setText(location.getTags().toString());
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
            TextView categoryView;
            TextView tagsView;



            public GemsViewHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
                nameView = itemView.findViewById(R.id.textView16);
                categoryView = itemView.findViewById(R.id.textView17);
                tagsView = itemView.findViewById(R.id.textView18);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        action.gemShow(Locations.get(position).docID);
                    }
                });

            }


        }
    }


    public void missingInput(Context context) {
        Toast.makeText(context, getString(R.string.missing), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof locationGem){
            action = (locationGem) context;
        }
    }

    public static locationGem action;

    public interface locationGem{
        void gemShow(String id);
    }


}
