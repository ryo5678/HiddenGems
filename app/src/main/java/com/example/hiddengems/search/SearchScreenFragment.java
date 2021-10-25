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
        binding.searchSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text = binding.enterLocation.getText().toString();
                if (text.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    searchLocation(text);
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
        //getActivity().setTitle(SelectedFilter);
        return super.onOptionsItemSelected(item);
    }


    public void searchLocation(String text){
        //String token = mUserToken.token;
        /*FormBody formBody = new FormBody.Builder()
                .add("post_text", text)
                .build();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/create")
                .addHeader("Authorization", "BEARER "  + token)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d(TAG,body);
                    */
        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        //fm.popBackStack("Search", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                /*}
            }
        });*/
    }
    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }


}