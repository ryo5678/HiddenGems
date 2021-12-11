package com.example.hiddengems.home;

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
import android.widget.TextView;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.FragmentOurPicksBinding;
import com.example.hiddengems.databinding.FragmentWhatsNewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.hiddengems.dataModels.Locations.Location;

import java.util.ArrayList;

public class WhatsNewFragment extends Fragment {

    FragmentWhatsNewBinding binding;
    FirebaseAuth mAuth;
    ArrayList<Locations.Location> allLocations = new ArrayList<>();
    ArrayList<Locations.Location> finalLocations = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    GemsRecyclerViewAdapter adapter;


    public WhatsNewFragment() {
        // Required empty public constructor
    }


    public static WhatsNewFragment newInstance() {
        WhatsNewFragment fragment = new WhatsNewFragment();
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
        binding = FragmentWhatsNewBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Whats new!");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("locations")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        allLocations.clear();
                        finalLocations.clear();
                        for(QueryDocumentSnapshot document : value) {


                            Locations.Location newPlace = new Locations.Location(document.getId(), document.getString("Creator"),
                                    document.getString("Name"), document.getString("Category"),
                                    (ArrayList<String>) document.get("Tags"));
                            Log.d("Check","Adding Location");
                            allLocations.add(newPlace);
                        }
                        mAuth = FirebaseAuth.getInstance();
                        for (int i = 0; i < allLocations.size(); i++) {
                            String match = allLocations.get(i).Name;
                            if (match.equals("Heist Brewery") || match.equals("Haberdish")) {
                                finalLocations.add(allLocations.get(i));
                            }
                        }

                        recyclerView = binding.picksRecycler3;
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new GemsRecyclerViewAdapter(finalLocations);
                        recyclerView.setAdapter(adapter);
                    }
                });



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

                        action.pickShow2(Locations.get(position).docID);
                    }
                });

            }


        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ourPick){
            action = (ourPick) context;
        }
    }

    public static ourPick action;

    public interface ourPick{
        void pickShow2(String id);
    }
}