

package com.example.hiddengems.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentHoursChangeBinding;
import com.example.hiddengems.databinding.FragmentLocationRemovalBinding;
import com.example.hiddengems.databinding.FragmentMyGemsBinding;


public class MyGemsFragment extends Fragment {

    FragmentMyGemsBinding binding;
    String text;

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
        getActivity().setTitle("My Gems");

        binding.myGems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = binding.gemss.getText().toString();
                if (text.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    submitTags(text);
                }
            }
        });


    }

    public void submitTags(String text) {
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
        fm.popBackStack("gems", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                /*}
            }
        });*/
    }

    public void missingInput(Context context) {
        Toast.makeText(context, getString(R.string.missing), Toast.LENGTH_SHORT).show();
    }
}
