package com.example.hiddengems.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiddengems.R;
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
       // LocationView example = getView().findViewById(R.id.example_test);
        //String[] check = {"Hello"};
       // location textloc = new location("Big daddy","117 cheese drive", "Bar", check );
        //example.setLocation(textloc,0.9);
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
        binding.searchSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text = binding.enterLocation.getText().toString();
                if (text.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    //submitLocation(text);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SelectedFilter = (String) item.getTitle();
        //Log.d("selecteditem", SelectedFilter);
        if (notTitle(SelectedFilter)) {
            getActivity().setTitle("Search by: " + SelectedFilter);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean notTitle(String selectedItem) {

        if (selectedItem.equals("Start Menu")) {
            return false;
        } else if (selectedItem.equals("Categories")) {
            return false;
        } else if (selectedItem.equals("Tags")) {
            return false;
        }else if (selectedItem.equals("Ratings")) {
            return false;
        }else if (selectedItem.equals("Seasons")) {
            return false;
        }else  {
            return true;
        }

    }

    public void submitLocation(String text){

    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }

}