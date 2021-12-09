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
import com.example.hiddengems.databinding.FragmentHoursChangeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;


public class HoursChangeFragment extends Fragment {

    FragmentHoursChangeBinding binding;
    String placeName;
    String placeHours;
    String id;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseAuth mAuth;
    FirebaseUser user;

    public HoursChangeFragment() {
        // Required empty public constructor
    }


    public static HoursChangeFragment newInstance() {
        HoursChangeFragment fragment = new HoursChangeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHoursChangeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Request Operating Hours");
        binding.OperatingHoursSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                placeName = binding.enterLocName.getText().toString();
                placeHours = binding.enterLocName2.getText().toString();
                if (placeName.isEmpty() || placeHours.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    submitHours(placeName, placeHours);
                }
            }
        });
    }

    public void submitHours(String placeName, String placeHours){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> hourChange = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        hourChange.put("place", placeName);
        hourChange.put("hours", placeHours);

        db.collection("opHours")
                .add(hourChange);


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
        fm.popBackStack("operatingHoursRequest", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                /*}
            }
        });*/
    }
    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }


}