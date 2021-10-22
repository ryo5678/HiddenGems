package com.example.hiddengems.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentRequestTagsBinding;


import java.io.IOException;


public class RequestTagsFragment extends Fragment {

    FragmentRequestTagsBinding binding;
    String text;

    public RequestTagsFragment() {
        // Required empty public constructor
    }


    public static RequestTagsFragment newInstance() {
        RequestTagsFragment fragment = new RequestTagsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestTagsBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Request Tags");
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text = binding.enterTags.getText().toString();
                if (text.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    submitTags(text);
                }
            }
        });
    }

    public void submitTags(String text){
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
                    fm.popBackStack("TagRequest", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                /*}
            }
        });*/
    }
    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }
}