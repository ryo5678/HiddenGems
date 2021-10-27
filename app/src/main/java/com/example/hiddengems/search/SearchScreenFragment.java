package com.example.hiddengems.search;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentRequestTagsBinding;
import com.example.hiddengems.databinding.FragmentSearchScreenBinding;


public class SearchScreenFragment extends Fragment {

    String SelectedFilter;
    FragmentSearchScreenBinding binding;
    String text;

    public SearchScreenFragment() {
        // Required empty public constructor
    }



    public static SearchScreenFragment newInstance(String param1, String param2) {
        SearchScreenFragment fragment = new SearchScreenFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SelectedFilter = (String) item.getTitle();
        //Log.d("selecteditem", SelectedFilter);
        getActivity().setTitle("Search by: " + SelectedFilter);
        return super.onOptionsItemSelected(item);
    }

}