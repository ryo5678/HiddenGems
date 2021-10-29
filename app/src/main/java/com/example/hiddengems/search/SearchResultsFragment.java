package com.example.hiddengems.search;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hiddengems.R;

import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.FragmentSearchResultsBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment {

    ArrayList<Locations> locationList;
    FragmentSearchResultsBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    LocationRecyclerViewAdapter adapter;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchResultsFragment newInstance(ArrayList<Locations> Locations) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putSerializable("searchList", Locations);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locationList = (ArrayList<Locations>) getArguments().getSerializable("searchList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchResultsBinding.inflate(inflater,container,false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LocationRecyclerViewAdapter(locationList);
        recyclerView.setAdapter(adapter);

    }

    public static class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder> {
        ArrayList<Locations> Locations;
        public LocationRecyclerViewAdapter(ArrayList<Locations> data) {
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
            Locations location = Locations.get(position);
            holder.position = position;

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
                        // Go to some another view with just this location
                        // Get location with this locations.get(position).locationID
                    }
                });

            }


        }
    }
}